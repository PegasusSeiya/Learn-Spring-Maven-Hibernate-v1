package fr.todooz.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton // instance unique de ce service dans l'application
@Startup // instancié au démarrage de l'application
public class HelloEJB {
    @PostConstruct
    public void printHello() {
        System.out.println("\n\n\nhello app server\n\n\n");
        
    }
    
    public String sayHello(String name){
    	return "Hi "+name;
    }
}