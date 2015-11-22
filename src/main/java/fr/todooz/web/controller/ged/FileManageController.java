package fr.todooz.web.controller.ged;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import fr.todooz.web.controller.ged.form.FileUploadForm;
import fr.todooz.web.controller.ged.services.FileManageService;

@Controller
@RequestMapping( "fileManage" )
public class FileManageController {

	private static final String VUE_UPLOAD 			= "upload";
	private static final String VUE_UPLOAD_JSP 		= "/WEB-INF/jsp/upload.jsp";
	private static final String FILE_UPLOAD_FORM 	= "ged/file_upload_form";
	private static final String FILE_UPLOAD_SUCCESS = "ged/file_upload_success";
	private static final String CHAMP_DESCRIPTION 	= "description";
	private static final String CHAMP_FICHIER     	= "fichier";
	private static final String CHEMIN        		= "chemin";

	private static final String SERVER_REPO_PATH    = "c:/upload_folder/";
	
	private static final int TAILLE_TAMPON 			= 10240; // 10 ko
	private static final int DEFAULT_BUFFER_SIZE 	= 10240; // 10 ko

	
	@Inject
	private FileManageService fileManageService;
	
	@RequestMapping( value = "uploadInvite", method = RequestMethod.GET )
    public String uploadInvite() {
		/* Affichage de la page d'envoi de fichiers */

		return FILE_UPLOAD_FORM;
    }
	
	@RequestMapping( value = "upload", method = RequestMethod.POST )
    public String upload(@ModelAttribute("uploadForm") FileUploadForm uploadForm,
            Model map ) throws IOException {
		
		List<MultipartFile> files = uploadForm.getFiles();
		 
        List<String> fileNames = new ArrayList<String>();
        
        String chemin = SERVER_REPO_PATH;
         
        if(null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
 
                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);
                
                //Handle file content - multipartFile.getInputStream()
                fileManageService.ecrireFichierServer(multipartFile.getInputStream(), fileName, chemin);

 
            }
        }
         
        map.addAttribute("files", fileNames);
        return FILE_UPLOAD_SUCCESS;

    }
	
	
	@RequestMapping( value = "listFileUploaded", 
					 method = RequestMethod.GET,
					 produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String listFileUploaded( HttpServletRequest request, HttpServletResponse response ){
		
		//AJAX CALL!
		
		//Chemin du repertoire sur le serveur contenant les fichiers uploaded
		String chemin = SERVER_REPO_PATH;
		
		//Création de l'objet file représentant le répertoire
		File folder = new File(chemin);
		
		File[] files = folder.listFiles();
		
		if (files != null && files.length > 0){
						
			List<String> fileNames = new ArrayList<String>();
			
			List<File> filesList = Arrays.asList(files);
			
			for (File file: filesList){
				
				if (file.isFile()){
					System.out.println("File name :" + file.getName());
					fileNames.add( file.getName());
					
				}
			}
			
			Gson gson = new Gson();
			
			return gson.toJson(fileNames);
			
		}

		
		return "";
	}

	
	@RequestMapping( value = "download", method = RequestMethod.GET )
	public void download( HttpServletRequest request, HttpServletResponse response ) throws FileNotFoundException, IOException{
		/* Lecture du paramètre 'chemin' passé à la servlet via la
		déclaration dans le web.xml */
		String chemin = SERVER_REPO_PATH;
		
		/* Récupération du chemin du fichier demandé au sein de l'URL de la
		requête */
		String fichierRequis = request.getParameter("file");
		
		System.out.println("download chemin :" + chemin);
		System.out.println("download fichierRequis :" + fichierRequis);
		
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
