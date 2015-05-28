package fr.todooz.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskService;
import fr.todooz.util.TagCloud;

@Controller
public class IndexController {
    @Inject
    @Qualifier( "hibernate" )
    private TaskService     taskService;

    @Inject
    private TagCloudService tagCloudService;

    @PostConstruct
    public void initializeTaskSet() {
        if ( taskService.count() == 0 ) {
            saveSomeTasks();
        }
    }

    @PreDestroy
    public void cleanTaskSet() {
        taskService.deleteAll();
    }

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

    @RequestMapping( { "/", "/index" } )
    public String index( Model model ) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute( "tasks", tasks );

        // Todo: A supprimer: model.addAttribute( "tagCloud",
        // tagCloudService.buildTagCloud() );

        return "index";
    }

    @RequestMapping( "/hello" )
    @ResponseBody
    public String hello() {
        return "Hello world";
    }

    @RequestMapping( "/search" )
    public String search( String query, Model model ) {
        List<Task> taskQueried = taskService.findByQuery( query );
        model.addAttribute( "tasks", taskQueried );

        // Todo: A supprimer: model.addAttribute( "tagCloud",
        // tagCloudService.buildTagCloud() );

        return "index";
    }

    @RequestMapping( "/tag/{tag}" )
    public String tag( @PathVariable String tag, Model model ) {
        List<Task> taskQueried = taskService.findByTag( tag );
        model.addAttribute( "tasks", taskQueried );

        // Todo: A supprimer: model.addAttribute( "tagCloud",
        // tagCloudService.buildTagCloud() );

        return "index";
    }

    @RequestMapping( "/deadLine/{queryDeadline}" )
    public String deadLine( @PathVariable String queryDeadline, Model model ) {

        List<Task> taskQueried = taskService.findByDate( queryDeadline );
        model.addAttribute( "tasks", taskQueried );

        return "index";
    }

    private void saveSomeTasks() {

        taskService.save( buildTask( "Read Effective Java, Play with Cobol",
                "Read Effective Java before it's too late", "java,cobol,effective" ) );
        taskService.save( buildTask( "Java vs Python", "Do Java or Python", "java,python" ) );
        taskService.save( buildTask( "Ruby and Python", "Ruby on Rails or Django", "ruby,python,django" ) );
        taskService.save( buildTask( "NodeJS", "Responsive application", "nodejs" ) );

    }

    private Task buildTask( String title, String text, String tags ) {
        Task task = new Task();
        task.setDate( new Date() );
        task.setTitle( title );
        task.setText( text );
        task.setTags( tags );
        return task;
    }
}