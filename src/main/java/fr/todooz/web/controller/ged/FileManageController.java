package fr.todooz.web.controller.ged;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import fr.todooz.web.controller.ged.services.FileManageService;

@Controller
@RequestMapping( "fileManage" )
@WebInitParam( name = "chemin", value = "/upload_folder/" )
@MultipartConfig( location = "c:/upload_folder", maxFileSize = 2 * 1024 * 1024,
maxRequestSize = 10 * 1024 * 1024, fileSizeThreshold = 1024 * 1024 )
public class FileManageController {
	
	public static final String VUE_UPLOAD 			= "upload";
	public static final String VUE_UPLOAD_JSP 		= "/WEB-INF/jsp/upload.jsp";
	public static final String CHAMP_DESCRIPTION 	= "description";
	public static final String CHAMP_FICHIER     	= "fichier";
	public static final String CHEMIN        		= "chemin";
	
	public static final int TAILLE_TAMPON 			= 10240; // 10 ko
	private static final int DEFAULT_BUFFER_SIZE 	= 10240; // 10 ko
	
	@Inject
	private ServletContext servletContext;
	
	@Inject
	private FileManageService fileManageService;
	
	@RequestMapping( "uploadInvite" )
    public String uploadInvite() {
		/* Affichage de la page d'envoi de fichiers */

		return VUE_UPLOAD;
    }
	
	@RequestMapping( value = "upload", method = RequestMethod.POST )
    public String upload( HttpServletRequest request, HttpServletResponse response ) throws IOException, ServletException {
		/*
		* Lecture du paramètre 'chemin' passé à la servlet via la déclaration
		* dans le web.xml
		*/
		
		String chemin = request.getServletContext().getInitParameter( CHEMIN );
		
		/* Récupération du contenu du champ de description */
		String description = request.getParameter( CHAMP_DESCRIPTION );
		request.setAttribute( CHAMP_DESCRIPTION, description );
		
		/*
		* Les données reçues sont multipart, on doit donc utiliser la méthode
		* getPart() pour traiter le champ d'envoi de fichiers.
		*/
		
		System.out.println("chemin :"+chemin);
		System.out.println("description :"+description);
		System.out.println("part getParts :"+request.getParts());
		
		Part part = request.getPart( CHAMP_FICHIER );
		
		/*
		* Il faut déterminer s'il s'agit d'un champ classique
		* ou d'un champ de type fichier : on délègue cette opération
		* à la méthode utilitaire getNomFichier().
		*/
		String nomFichier = fileManageService.getNomFichier( part );
		
		/*
		* Si la méthode a renvoyé quelque chose, il s'agit donc d'un champ
		* de type fichier (input type="file").
		*/
		if ( nomFichier != null && !nomFichier.isEmpty() ) {
			String nomChamp = part.getName();
			/*
			* Antibug pour Internet Explorer, qui transmet pour une raison
			* mystique le chemin du fichier local à la machine du client...
			* Ex : C:/dossier/sous-dossier/fichier.ext
			* 
			* On doit donc faire en sorte de ne sélectionner que le nom et
			* l'extension du fichier, et de se débarrasser du superflu.
			*/
			nomFichier = nomFichier
						.substring( nomFichier.lastIndexOf( '/' ) + 1 )
						.substring( nomFichier.lastIndexOf( '\\' ) + 1 );
			
			/* Écriture du fichier sur le disque */
			fileManageService.ecrireFichierServer( part, nomFichier, chemin );
			
			request.setAttribute( nomChamp, nomFichier );
		}
		
		/*
		* Si la méthode a renvoyé quelque chose, il s'agit donc d'un champ
		* de type fichier (input type="file").
		*/
		if ( nomFichier != null && !nomFichier.isEmpty() ) {
			String nomChamp = part.getName();
			request.setAttribute( nomChamp, nomFichier );
		}

		return VUE_UPLOAD_JSP;
    }

	
	@RequestMapping( value = "download", method = RequestMethod.GET )
	public void download( HttpServletRequest request, HttpServletResponse response ) throws FileNotFoundException, IOException{
		/* Lecture du paramètre 'chemin' passé à la servlet via la
		déclaration dans le web.xml */
		String chemin = request.getServletContext().getInitParameter( CHEMIN );
		
		/* Récupération du chemin du fichier demandé au sein de l'URL de la
		requête */
		String fichierRequis = request.getPathInfo();
		
		/* Vérifie qu'un fichier a bien été fourni */
		if ( fichierRequis == null || "/".equals( fichierRequis ) ) {
			/* Si non, alors on envoie une erreur 404, qui signifie que la
			ressource demandée n'existe pas */
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		/* Décode le nom de fichier récupéré, susceptible de contenir des
		espaces et autres caractères spéciaux, et prépare l'objet File */
		fichierRequis = URLDecoder.decode( fichierRequis, "UTF-8");
		
		File fichier = new File( chemin, fichierRequis );
		
		/* Vérifie que le fichier existe bien */
		if ( !fichier.exists() ) {
			/* Si non, alors on envoie une erreur 404, qui signifie que la
			ressource demandée n'existe pas */
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			
			return;
		}
		
		/* Récupère le type du fichier */
		String type = request.getServletContext().getMimeType( fichier.getName() );
		/* Si le type de fichier est inconnu, alors on initialise un type
		par défaut */
		if ( type == null ) {
			type = "application/octet-stream";
		}
		
		/* Initialise la réponse HTTP */
		response.reset();
		response.setBufferSize( DEFAULT_BUFFER_SIZE );
		response.setContentType( type );
		response.setHeader( "Content-Length", 
				String.valueOf( fichier.length() ) );
		response.setHeader( "Content-Disposition", "attachment; filename=\""
					+ fichier.getName() + "\"" );
		
		
		fileManageService.ecrireFichierClient(response, fichier);
		
		return;
	}
}
