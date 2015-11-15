package fr.todooz.web.controller.ged;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import fr.todooz.util.ValidatorTools;
import fr.todooz.web.controller.ged.form.FileUploadForm;
import fr.todooz.web.controller.ged.services.FileManageService;

@Controller
@RequestMapping( "fileManage" )
public class FileManageController {
	
	public static final String VUE_UPLOAD 			= "ged/file_upload_form";
	public static final String VUE_UPLOAD_SUCCESS	= "ged/file_upload_success";
	public static final String CHAMP_DESCRIPTION 	= "description";
	public static final String CHAMP_FICHIER     	= "fichier";
	public static final String FILES     			= "files";
	public static final String CHEMIN        		= "chemin";
	
	public static final int TAILLE_TAMPON 			= 10240; // 10 ko
	private static final int DEFAULT_BUFFER_SIZE 	= 10240; // 10 ko

	
	@Inject
	private FileManageService fileManageService;
	
	@RequestMapping( "uploadInvite" )
    public String uploadInvite() {
		/* Affichage de la page d'envoi de fichiers */

		return VUE_UPLOAD;
    }
	
	@RequestMapping( value = "upload", method = RequestMethod.POST )
    public String upload( @ModelAttribute("uploadForm") FileUploadForm uploadForm,
            Model map ){
		List<MultipartFile> files = uploadForm.getFiles();
		 
        List<String> fileNames = new ArrayList<String>();
         
        if (ValidatorTools.isNotNullAndNotEmpty(files)) {
        	
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                //Handle file content - multipartFile.getInputStream()
 
            }
        }
         
        map.addAttribute(FILES, fileNames);
        
        return VUE_UPLOAD_SUCCESS;
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
