package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;

@Service
@Qualifier( "hibernate" )
public class TaskServiceImpl implements TaskService {
    @Inject
    private SessionFactory sessionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see fr.todooz.service.TaskService#save(fr.todooz.domain.Task)
     */
    /**
     * Save instance in base.
     */
    @Override
    @Transactional
    public void save( Task task ) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate( task );
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.todooz.service.TaskService#delete(java.lang.Long)
     */
    /**
     * Delete in base the instance with this id.
     */
    @Override
    @Transactional
    public void delete( Long id ) {
        Task taskToDelete = findById( id );
        Session session = sessionFactory.getCurrentSession();

        session.delete( taskToDelete );
    }

    /**
     * Delete in base all instances.
     */
    @Override
    @Transactional
    public void deleteAll() {
        List<Task> tasksToDelete = findAll();
        for ( Task task : tasksToDelete ) {
            delete( task.getId() );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.todooz.service.TaskService#findAll()
     */
    /**
     * Retrieve all instances of the table Task.
     */
    @Override
    @Transactional(readOnly = true) // readOnly = true, 
    public List<Task> findAll() {
    	
    	Session session = null;
    			
    	try{
    		session = sessionFactory.getCurrentSession();
    	}catch(org.hibernate.HibernateException he){
    		session = sessionFactory.openSession();
    	}
        

        Query query = session.createQuery( "from Task" );

        List<Task> tasks = query.list();

        return tasks;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.todooz.service.TaskService#findById(java.lang.Long)
     */
    /**
     * Retrive the instance with this id.
     */
    @Override
    @Transactional( readOnly = true )
    public Task findById( Long id ) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery( "from Task where id = :id" );

        query.setLong( "id", id );

        Task task = (Task) query.uniqueResult();

        return task;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.todooz.service.TaskService#findByQuery(java.lang.String)
     */
    /**
     * Retrieve the instance with this title as query parameter.
     */
    @Override
    @Transactional( readOnly = true )
    public List<Task> findByQuery( String titleQuery ) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery( "from Task where title like :title" );

        query.setString( "title", "%" + titleQuery + "%" );

        List<Task> tasks = query.list();

        return tasks;
    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.todooz.service.TaskService#count()
     */
    /**
     * Count the number of instances in base.
     */
    @Override
    @Transactional( readOnly = true )
    /*
     * Annotation @Transactional mandatory here, isnt transitive.
     */
    public int count() {
        List<Task> tasks = findAll();
        return ( tasks != null ) ? tasks.size() : 0;
    }

    @Override
    @Transactional( readOnly = true )
    public List findByTag( String tag ) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery( "from Task where tags like :tag" );

        query.setString( "tag", "%" + tag + "%" );

        List<Task> tasks = query.list();

        return tasks;
    }

    @Override
    @Transactional( readOnly = true )
    public List findByDate( String queryDeadline ) {
        Session session = sessionFactory.getCurrentSession();

        /*
         * With Criteria API
         */
        Criteria criteria = session.createCriteria( Task.class );

        DateTime today = new DateTime().withTimeAtStartOfDay();
        int daysOfDelay = 1;

        if ( "today".equals( queryDeadline ) ) {
            criteria.add( Restrictions.between( "date", today.toDate(), today.plusDays( daysOfDelay ).toDate() ) );
        } else {
            criteria.add( Restrictions.gt( "date", today.plusDays( daysOfDelay ).toDate() ) );
        }

        List<Task> tasks = criteria.list();

        return tasks;
    }
}
