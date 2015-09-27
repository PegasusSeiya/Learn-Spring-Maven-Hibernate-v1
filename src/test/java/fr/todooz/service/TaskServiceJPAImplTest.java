package fr.todooz.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.todooz.domain.Task;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class TaskServiceJPAImplTest {
	
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Inject
    @Qualifier( "jpa" )
    private TaskService          taskService;

    @After
    @Ignore
    public void cleanDb() {
        /*
         * EntityManager entityManager =
         * entityManagerFactory.createEntityManager();
         * 
         * EntityTransaction entityTransaction = entityManager.getTransaction();
         * 
         * entityTransaction.begin();
         * 
         * entityManager.createQuery( "delete from Task" ).executeUpdate();
         * 
         * entityTransaction.commit();
         * 
         * entityManager.close();
         */

    }

    @Test
   //@Ignore
    public void save() {
        /*
         * Task task = task();
         * 
         * taskService.save( task );
         * 
         * Assert.assertNotNull( "Null or no task Id! ", task.getId() );
         */
    	
    	Task task = task();
    	taskService.save( task );
    	Assert.assertNotNull( "Null or no task Id! ", task.getId() );
    	Assert.assertEquals( "0", task.getId() );
    }
    
    @Test
    public void findAll(){
    	List<Task> tasks = taskService.findAll();
    	
    	Assert.assertNotNull( "Null or no task ", tasks);
    	Assert.assertEquals( 1, tasks.size() );
    }

    private Task task() {
        return task( "java,jpa" );
    }

    private Task task( String tags ) {
        return task( new Date(), tags );
    }

    private Task task( Date date, String tags ) {
        Task task = new Task();
        task.setDate( date );
        task.setTitle( "JPA EntityManager FTW!" );
        task.setText( "Use some stuff about JPA" );
        task.setTags( tags );
        return task;
    }

}
