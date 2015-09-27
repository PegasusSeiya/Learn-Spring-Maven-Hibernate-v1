package fr.todooz.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
@Qualifier( "jpa" )
public class TaskServiceJPAImpl implements TaskService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional( "jpa" )
    public void save( Task task ) {
        entityManager.persist( task );

    }

    @Override
    public void delete( Long id ) {
        // TODO Auto-generated method stub

    }

    @Override
    @Transactional( "jpa" )
    public List<Task> findAll() {
    	//TypedQuery<Task> query = entityManager.createQuery("Select t from Task", Task.class);

        List<Task> tasks = getSomeTasks(); //query.getResultList();

        return tasks;
    }

    @Override
    public Task findById( Long id ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Task> findByQuery( String titleQuery ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public List findByTag( String tag ) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List findByDate( String queryDeadline ) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    private List<Task> getSomeTasks() {
    	List<Task> tasks = new ArrayList<Task>();
    	
    	tasks.add( buildTask( "Read Effective Java, Play with Cobol",
                "Read Effective Java before it's too late", "java,cobol,effective" ) );
    	tasks.add( buildTask( "Java vs Python", "Do Java or Python", "java,python" ) );
    	tasks.add( buildTask( "Ruby and Python", "Ruby on Rails or Django", "ruby,python,django" ) );
    	tasks.add( buildTask( "NodeJS", "Responsive application", "nodejs" ) );
    	
    	return tasks;

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
