package fr.todooz.listeners;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Qualifier;

import fr.todooz.service.TaskService;

//@WebListener
public class TodoozContextListener implements ServletContextListener, HttpSessionListener {
     
	@Inject
	@Qualifier( "hibernate" )
	private TaskService taskService;
    
    
    private static java.util.Map<String, TaskService> services = new HashMap<String, TaskService>();
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	System.out.println(">>>>>>>>>>>>>>>    The TodoozContextListener has been initialized.");
    	System.out.println(">>>>>>>>>>>>>>>    TodoozContextListener contextInitialized taskService :"+taskService);
    	 services.put("taskService", taskService);
    	 System.out.println(">>>>>>>>>>>>>>>    TodoozContextListener contextInitialized services :"+services);
    	        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    	System.out.println(">>>>>>>>>>>>>>>    The TodoozContextListener has been destroyed.");
    	services = null;
        
    }
    
    public static TaskService getTaskService() {
        return  services.get("taskService");
    }
    
   
    

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		System.out.println("The session has been initialized.");
		System.out.println(">>>>>>>>>>>>>>>    TodoozContextListener sessionCreated taskService :"+taskService);
   	 	services.put("taskService", taskService);
   	 	System.out.println(">>>>>>>>>>>>>>>    TodoozContextListener sessionCreated services :"+services);
   	 
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
		System.out.println("The session has been destroyed.");
	}
}
