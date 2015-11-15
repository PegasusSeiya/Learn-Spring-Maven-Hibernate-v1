package fr.todooz.web.servlet.ged;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Upload
 */
public class Upload extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public static final String VUE 					= "/WEB-INF/jsp/upload.jsp";
	public static final String CHAMP_DESCRIPTION 	= "description";
	public static final String CHAMP_FICHIER     	= "fichier";
	public static final String CHEMIN        		= "chemin";
	public static final int TAILLE_TAMPON 			= 10240; // 10 ko
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Upload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* Affichage de la page d'envoi de fichiers */
		this.getServletContext().getRequestDispatcher( VUE ).forward(
		request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/*
		* Lecture du paramètre 'chemin' passé à la servlet via la déclaration
		* dans le web.xml
		*/
		String chemin = this.getServletConfig().getInitParameter( CHEMIN );
		
		/* Récupération du contenu du champ de description */
		String description = request.getParameter( CHAMP_DESCRIPTION );
		request.setAttribute( CHAMP_DESCRIPTION, description );
		
		/*
		* Les données reçues sont multipart, on doit donc utiliser la méthode
		* getPart() pour traiter le champ d'envoi de fichiers.
		*/
		Part part = request.getPart( CHAMP_FICHIER );
		
		/*
		* Il faut déterminer s'il s'agit d'un champ classique
		* ou d'un champ de type fichier : on délègue cette opération
		* à la méthode utilitaire getNomFichier().
		*/
		String nomFichier = getNomFichier( part );
		
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
			ecrireFichierServer( part, nomFichier, chemin );
			
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
		this.getServletContext()
			.getRequestDispatcher( VUE )
			.forward( request, response );
	}
	
	
	/*
	* Méthode utilitaire qui a pour unique but d'analyser l'en-tête
	"content-disposition",
	* et de vérifier si le paramètre "filename" y est présent. Si oui,
	alors le champ traité
	* est de type File et la méthode retourne son nom, sinon il s'agit
	d'un champ de formulaire
	* classique et la méthode retourne null.
	*/
	private static String getNomFichier( Part part ) {
		/* Boucle sur chacun des paramètres de l'en-tête "contentdisposition". */
		/*
		System.out.println("part :"+part);
		System.out.println("part getHeaderNames :"+part.getHeaderNames());
		System.out.println("header contentdisposition :"+part.getHeader( "Content-Disposition" ));
		*/
		
		String[] contentHeader = part.getHeader( "Content-Disposition" ).split( ";" );
		for ( String contentDisposition : contentHeader ) {
			/* Recherche de l'éventuelle présence du paramètre "filename".
			*/
			if ( contentDisposition.trim().startsWith("filename") ) {
				/*
				* Si "filename" est présent, alors renvoi de sa valeur,
				* c'est-à-dire du nom de fichier sans guillemets.
				*/
				return contentDisposition
						.substring(contentDisposition.indexOf( '=' ) + 1 )
						.trim()
						.replace( "\"", "" );
				
			}
		}
		/* Et pour terminer, si rien n'a été trouvé... */
		return null;
	}
	
	
	/*
	* Méthode utilitaire qui a pour but d'écrire le fichier passé en
	paramètre
	* sur le disque, dans le répertoire donné et avec le nom donné.
	*/
	private void ecrireFichierServer( Part part, String nomFichier, String
	chemin ) throws IOException {
		/* Prépare les flux. */
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		
		try {
			/* Ouvre les flux. */
			entree = new BufferedInputStream( part.getInputStream(), TAILLE_TAMPON );
			sortie = new BufferedOutputStream( 
					 	new FileOutputStream( 
					 			new File( chemin + nomFichier ) ),
					 				TAILLE_TAMPON );
			/*
			* Lit le fichier reçu et écrit son contenu dans un fichier sur le
			* disque.
			*/
			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ( ( longueur = entree.read( tampon ) ) > 0 ) {
				sortie.write( tampon, 0, longueur );
			}
		} finally {
			try {
				sortie.close();
			} catch ( IOException ignore ) {
			}
			try {
				entree.close();
			} catch ( IOException ignore ) {
			}
		}
	}

}
