package fr.todooz.rest;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
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
public class TasksResource{
	
   @Inject
   @Qualifier( "hibernate" )
   private TaskService taskService;
	
	
   @GET
   @Produces(MediaType.APPLICATION_JSON)
   public List<Task> tasks() {

	   List<Task> tasks = taskService.findAll();
	   
       return tasks;
   }
}