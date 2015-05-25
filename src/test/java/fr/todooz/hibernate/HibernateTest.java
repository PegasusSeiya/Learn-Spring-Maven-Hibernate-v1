package fr.todooz.hibernate;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.todooz.domain.Task;
import fr.todooz.service.TaskService;
import fr.todooz.service.TaskServiceImpl;

public class HibernateTest {

    private SessionFactory sessionFactory = null;

    @Before
    public void createSessionFactory() {
        Configuration configuration = new Configuration();

        configuration.setProperty( "hibernate.dialect", "org.hibernate.dialect.DerbyTenFiveDialect" );
        configuration.setProperty( "hibernate.connection.url", "jdbc:derby:target/testdb;create=true" );
        configuration.setProperty( "hibernate.connection.driver_class", "org.apache.derby.jdbc.EmbeddedDriver" );
        configuration.setProperty( "hibernate.hbm2ddl.auto", "create-drop" );

        // Informer Hibernate de la présence du bean Task
        configuration.addAnnotatedClass( Task.class );

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                .applySettings( configuration.getProperties() ).buildServiceRegistry();

        sessionFactory = configuration.buildSessionFactory( serviceRegistry );
    }

    @After
    public void cleanDb() {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        session.createQuery( "delete from Task" ).executeUpdate();

        transaction.commit();

        session.close();

        sessionFactory.close();
    }

    @Test
    public void saveTask() {
        TaskService taskService = new TaskServiceImpl();
        taskService.setSessionFactory( sessionFactory );

        taskService.save( task() );
    }

    @Test
    public void delete() {
        TaskService taskService = new TaskServiceImpl();
        taskService.setSessionFactory( sessionFactory );
        Task task = task();
        taskService.save( task );

        taskService.delete( task.getId() );

        Session session = sessionFactory.openSession();

        Assert.assertEquals( 0, session.createQuery( "from Task" ).list().size() );

        session.close();
    }

    @Test
    public void findTaskById() {
        TaskService taskService = new TaskServiceImpl();
        taskService.setSessionFactory( sessionFactory );
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
        TaskService taskService = new TaskServiceImpl();
        taskService.setSessionFactory( sessionFactory );

        taskService.save( task() );
        taskService.save( task() );

        Assert.assertEquals( 2, taskService.findAll().size() );
    }

    @Test
    public void findByQuery() {
        TaskService taskService = new TaskServiceImpl();
        taskService.setSessionFactory( sessionFactory );

        taskService.save( task() );
        taskService.save( task() );

        Assert.assertEquals( 2, taskService.findByQuery( "Read" ).size() );
        Assert.assertEquals( 2, taskService.findByQuery( "Java" ).size() );
        Assert.assertEquals( 0, taskService.findByQuery( "Driven" ).size() );
    }

    @Test
    public void count() {
        TaskService taskService = new TaskServiceImpl();
        taskService.setSessionFactory( sessionFactory );

        taskService.save( task() );
        taskService.save( task() );

        Assert.assertEquals( 2, taskService.count() );
    }

    private Task task() {
        Task task = new Task();
        task.setDate( new Date() );
        task.setTitle( "Read Effective Java" );
        task.setText( "Read Effective Java before it's too late" );
        task.setTags( "java,java" );
        return task;
    }

}
