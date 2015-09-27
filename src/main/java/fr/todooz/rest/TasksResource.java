package fr.todooz.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Qualifier;

import fr.todooz.domain.Task;
import fr.todooz.service.TaskService;
import fr.todooz.web.controller.AdminController;

//@Component
@Path("/tasks")
//@RequestScoped
public class TasksResource extends AdminController{
	
	
   @Inject
   @Qualifier( "jpa" )
   private TaskService taskService;
	
	
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<Task> tasks() {
	   //TaskService taskService = TodoozContextListener.getTaskService();
	   //AdminController adminController = new AdminController();
	  //TaskService taskService = getTaskService(); //adminController.getTaskService();
	   
	   System.out.println(">>>>>>>>>>>>>>> TasksResource taskService :"+taskService);
	   
	   //return null;
       return (taskService != null) ? taskService.findAll() : null;
   }
}