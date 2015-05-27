package fr.todooz.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    public List<Task> findAll() {
        // TODO Auto-generated method stub
        return null;
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

}
