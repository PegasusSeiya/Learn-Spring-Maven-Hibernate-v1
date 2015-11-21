package fr.todooz.web.controller.ged.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public interface FileManageService {

	/**
	* Méthode utilitaire qui a pour unique but d'analyser l'en-tête "content-disposition",
	* et de vérifier si le paramètre "filename" y est présent. Si oui, alors le champ traité
	* est de type File et la méthode retourne son nom, sinon il s'agit d'un champ de formulaire
	* classique et la méthode retourne null.
	 * @param part Part
	 * @return String
	 */
	String getNomFichier(Part part);

	/**
	* Méthode utilitaire qui a pour but d'écrire le fichier passé en paramètre
	* sur le disque, dans le répertoire donné et avec le nom donné.
	*
	 * 
	 * @param part
	 * @param nomFichier
	 * @param chemin
	 * @throws IOException
	 */
	void ecrireFichierServer(InputStream inputStream, String nomFichier, String chemin) throws IOException;
	
	
	/**
	 * 
	 * @param response
	 * @param fichier
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	void ecrireFichierClient(HttpServletResponse response, File fichier)
			throws FileNotFoundException, IOException;
	
	
	
}