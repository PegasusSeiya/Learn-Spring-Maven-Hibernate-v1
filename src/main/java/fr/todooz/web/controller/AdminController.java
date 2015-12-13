package fr.todooz.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskMailerService;
import fr.todooz.service.TaskService;
import fr.todooz.util.TagCloud;

@Controller
public class AdminController {
    @Inject
    @Qualifier( "hibernate" )
    private TaskService         taskService;
    
    /*
    @Inject
    @Qualifier( "jpa" )
    private TaskService         taskServiceJPA;
    */

    @Inject
    private TagCloudService     tagCloudService;
    
    @Inject
    private TaskMailerService taskMailerService;
    
    private static final String rootPath = "/"; // "/J2EE-Spring-Maven-1.0/";
    
    
    /**
     * Un attribut TagCloud tagCloud va être ajouté au modèle quelle que soit la
     * méthode appelée.
     * 
     * @return
     */
    @ModelAttribute( value = "tagCloud" )
    public TagCloud tagCloud() {
        return tagCloudService.buildTagCloud();
    }

    /**
     * Configuration du DataBinder pour les champs Date.
     * 
     * @param binder
     */
    @InitBinder
    public void initBinder( DataBinder binder ) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat( "dd/MM/yyyy" );
        binder.registerCustomEditor( Date.class, new CustomDateEditor( dateFormatter, true ) );
    }

    @RequestMapping( "add" )
    public String add( Model model ) {
        // on injecte une Task vierge dans le modèle
        model.addAttribute( "task", new Task() );

        return "edit";
    }

    @RequestMapping( "edit/{id}" )
    public String edit( @PathVariable Long id, Model model ) {
        model.addAttribute( "task", taskService.findById( id ) );

        return "edit";
    }

    @RequestMapping( value = "edit", method = RequestMethod.POST )
    public String saveOrUpdate( @ModelAttribute @Valid Task task, BindingResult result,
            RedirectAttributes redirectAttributes ) {
        if ( result.hasErrors() ) {
            return "edit";
        }
        
        String changeState = "add";
        
        if ( task.getId() != null && !"".equals(task.getId())){
        	changeState = "modify";
		}
        
        taskMailerService.sendEmailWithTaskInfo(task, changeState);

        
        
        taskService.save( task );

        redirectAttributes.addFlashAttribute( "flashMessage", "Sauvegarde réussie." );

        return "redirect:" + rootPath;
    }

    @RequestMapping( "edit/{id}/delete" )
    public String delete( @PathVariable Long id, Model model, RedirectAttributes redirectAttributes ) {
        
    	Task taskToBeDeleted = taskService.findById( id );
    	
        model.addAttribute( "task", taskToBeDeleted );
        
        taskMailerService.sendEmailWithTaskInfo(taskToBeDeleted, "delete");
        
        taskService.delete( id );

        redirectAttributes.addFlashAttribute( "flashMessage", "Suppression réussie." );

        return "redirect:" + rootPath;
    }

}
