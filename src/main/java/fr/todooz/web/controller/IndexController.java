package fr.todooz.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    
    /*
     * Version 1
    @EJB(mappedName = "java:module/WelcomeEJB")
    private WelcomeEJB welcomeEJB;
    */
    
    /*
     * Version 2: Récupérer une référence vers l'ejb comme si c'était un composant Spring.
     */
    /*
    @Inject
    private WelcomeEJB welcomeEJB;
    */

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
    
    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

	  ModelAndView model = new ModelAndView();
	  model.addObject("title", "Spring Security Login Form - Database Authentication");
	  model.addObject("message", "This page is for ROLE_ADMIN only!");
	  model.setViewName("admin");
	  return model;

	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
							  @RequestParam(value = "logout", required = false) String logout) {

	  ModelAndView model = new ModelAndView();
	  if (error != null) {
		model.addObject("error", "Invalid username and password!");
	  }

	  if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	  }
	  model.setViewName("login");

	  return model;

	}
	
	//for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

	  ModelAndView model = new ModelAndView();
		
	  //check if user is login
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  if (!(auth instanceof AnonymousAuthenticationToken)) {
		UserDetails userDetail = (UserDetails) auth.getPrincipal();	
		model.addObject("username", userDetail.getUsername());
	  }
		
	  model.setViewName("403");
	  return model;

	}

    @RequestMapping( { "/", "index" } )
    public String index( Model model, HttpServletRequest request ) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute( "tasks", tasks );

        return "index";
    }

    @RequestMapping( "hello" )
    @ResponseBody
    public String hello() {

    	//return welcomeEJB.hello("EJBs from Controller");
        return "Hello world!";
    }

    @RequestMapping( "search" )
    public String search( String query, Model model ) {
        List<Task> taskQueried = taskService.findByQuery( query );
        model.addAttribute( "tasks", taskQueried );

        return "index";
    }

    @RequestMapping( "tag/{tag}" )
    public String tag( @PathVariable String tag, Model model ) {
        List<Task> taskQueried = taskService.findByTag( tag );
        model.addAttribute( "tasks", taskQueried );

        return "index";
    }

    @RequestMapping( "deadLine/{queryDeadline}" )
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