package fr.todooz.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.todooz.domain.Task;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration
public class TaskServiceTest {

    @Inject
    private SessionFactory sessionFactory;

    @Inject
    private TaskService    taskService;

    @After
    public void cleanDb() {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        session.createQuery( "delete from Task" ).executeUpdate();

        transaction.commit();

        session.close();
    }

    @Test
    public void saveTask() {

        taskService.save( task() );
    }

    @Test
    public void udpate() {
        Task task = task();

        taskService.save( task );
        taskService.save( task );

        Assert.assertEquals( 1, taskService.count() );
    }

    @Test
    public void delete() {
        Task task = task();
        taskService.save( task );

        taskService.delete( task.getId() );

        Session session = sessionFactory.openSession();

        Assert.assertEquals( 0, session.createQuery( "from Task" ).list().size() );

        session.close();
    }

    @Test
    public void findTaskById() {
        Task task = task();
        taskService.save( task );
        Long taskId = task.getId();

        Assert.assertEquals( taskId, taskService.findById( taskId ).getId() );

    }

    @Test
    public void findTask() {
        saveTask();

        Session session = sessionFactory.openSession();

        Query query = session.createQuery( "from Task where title = :title" );

        query.setString( "title", "Read Effective Java" );

        List<Task> tasks = query.list();

        session.close();

        Assert.assertEquals( 1, tasks.size() );
        Assert.assertEquals( "Read Effective Java", tasks.get( 0 ).getTitle() );
    }

    @Test
    public void findTaskWithCriteria() {
        saveTask();

        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria( Task.class );

        criteria.add( Restrictions.eq( "title", "Read Effective Java" ) );

        List<Task> tasks = criteria.list();

        session.close();

        Assert.assertEquals( 1, tasks.size() );
        Assert.assertEquals( "Read Effective Java", tasks.get( 0 ).getTitle() );
    }

    @Test
    public void findAll() {
        taskService.save( task() );
        taskService.save( task() );

        Assert.assertEquals( 2, taskService.findAll().size() );
    }

    @Test
    public void findByQuery() {
        taskService.save( task() );
        taskService.save( task() );

        assertEquals( 2, taskService.findByQuery( "Read" ).size() );
        assertEquals( 2, taskService.findByQuery( "Java" ).size() );
        assertEquals( 0, taskService.findByQuery( "Driven" ).size() );
    }

    @Test
    public void findByTag() {
        taskService.save( task( "java,j2EE,maven", 0 ) );
        taskService.save( task( "java,c++,ruby", 1 ) );
        taskService.save( task( "ruby,java,python", 2 ) );
        taskService.save( task( "mvc spring,struts,jsf", 3 ) );

        assertEquals( 3, taskService.findByTag( "java" ).size() );
        assertEquals( 2, taskService.findByTag( "ruby" ).size() );
    }

    @Test
    public void findByDate() {
        taskService.save( task( "java,j2EE,maven", 0 ) );
        taskService.save( task( "java,c++,ruby", 0 ) );
        taskService.save( task( "ruby,java,python", 1 ) );
        taskService.save( task( "mvc spring,struts,jsf", 2 ) );

        assertEquals( 2, taskService.findByDate( "today" ).size() );
        assertEquals( 2, taskService.findByDate( "tomorrow" ).size() );
    }

    @Test
    public void count() {
        taskService.save( task() );
        taskService.save( task() );

        assertEquals( 2, taskService.count() );
    }

    private Task task() {
        Task task = new Task();
        task.setDate( new Date() );
        task.setTitle( "Read Effective Java" );
        task.setText( "Read Effective Java before it's too late" );
        task.setTags( "java, uml" );
        return task;
    }

    private Task task( String tags, int daysOfDelay ) {
        Task task = new Task();
        Date dt = new Date();
        DateTime jodaDT = new DateTime( dt );
        DateTime jodaDTShift = jodaDT.plusDays( daysOfDelay );

        task.setDate( jodaDTShift.toDate() );
        task.setTitle( "Read Effective Java" );
        task.setText( "Read Effective Java before it's too late" );
        task.setTags( tags );
        return task;
    }

}
