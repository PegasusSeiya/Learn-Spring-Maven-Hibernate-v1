package fr.todooz.ejb;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class WelcomeEJB {
   @EJB
   private HelloEJB helloEJB;

   public String hello(String name) {
       return helloEJB.sayHello(name);
   }
   
   private int count = 0;

   @Schedule(hour = "*", minute = "*", second = "*/5")
   public void endlessly() {
       System.out.println(helloEJB.sayHello(" " + count));

       count++;
   }
}