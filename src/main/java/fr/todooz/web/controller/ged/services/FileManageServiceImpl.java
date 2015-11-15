package fr.todooz.web.controller.ged.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.stereotype.Service;

@Service
public class FileManageServiceImpl implements FileManageService{

	private static final int 	TAILLE_TAMPON 			= 10240; // 10 ko
	
	
	/*
	* Méthode utilitaire qui a pour unique but d'analyser l'en-tête
	"content-disposition",
	* et de vérifier si le paramètre "filename" y est présent. Si oui,
	alors le champ traité
	* est de type File et la méthode retourne son nom, sinon il s'agit
	d'un champ de formulaire
	* classique et la méthode retourne null.
	*/
	/* (non-Javadoc)
	 * @see fr.todooz.web.controller.ged.services.FileManageService#getNomFichier(javax.servlet.http.Part)
	 */
	@Override
	public String getNomFichier( Part part ) {
		/* Boucle sur chacun des paramètres de l'en-tête "contentdisposition". */
		
		System.out.println("part :"+part);		
		
		String[] contentHeader = null;
		
		if (part!=null && part.getHeader( "Content-Disposition" )!=null){
			
			System.out.println("part getHeaderNames :"+part.getHeaderNames());
			System.out.println("header contentdisposition :"+part.getHeader( "Content-Disposition" ));
			
			contentHeader = part.getHeader( "Content-Disposition" ).split( ";" );
			
			if (contentHeader!=null && contentHeader.length>0){
				
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
	/* (non-Javadoc)
	 * @see fr.todooz.web.controller.ged.services.FileManageService#ecrireFichierServer(javax.servlet.http.Part, java.lang.String, java.lang.String)
	 */
	@Override
	public void ecrireFichierServer( Part part, String nomFichier, String
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
	
	public void ecrireFichierClient(HttpServletResponse response, File fichier)
			throws FileNotFoundException, IOException {
		/* Prépare les flux */
		BufferedInputStream entree = null;
		BufferedOutputStream sortie = null;
		
		try {
			/* Ouvre les flux */
			entree = new BufferedInputStream( 
						new FileInputStream( fichier ), 
						TAILLE_TAMPON );
			sortie = new BufferedOutputStream( 
					response.getOutputStream(),
					TAILLE_TAMPON );

			/* Lit le fichier et écrit son contenu dans la réponse HTTP */
			byte[] tampon = new byte[TAILLE_TAMPON];
			int longueur;
			while ( ( longueur= entree.read( tampon ) ) > 0 ) {
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
