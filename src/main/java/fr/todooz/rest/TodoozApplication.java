package fr.todooz.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class TodoozApplication extends ResourceConfig {
    
	/**
	* Register JAX-RS application components.
	*/	
	public TodoozApplication() {
    	//register(RequestContextFilter.class);
    	//register(PodcastRestService.class);
    	//register(JacksonFeature.class);	
    	
		register(new MyApplicationBinder());
        // scan des ressources dans fr.todooz.rest
        //packages("fr.todooz.listeners, fr.todooz.web.controller, fr.todooz.rest, fr.todooz.services");
		packages(true, "fr.todooz.services", "fr.todooz.rest");
        //packages("fr.todooz.services");
    }
}

