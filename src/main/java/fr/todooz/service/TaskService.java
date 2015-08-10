package fr.todooz.service;

import java.util.List;

import fr.todooz.domain.Task;

public interface TaskService {

    public void save( Task task );

    public void delete( Long id );

    public List<Task> findAll();

    public Task findById( Long id );

    public List<Task> findByQuery( String titleQuery );

    /**
     * Count all items in the table Task.
     */
    public int count();

    void deleteAll();

    public List findByTag( String tag );

    /**
     * Select tasks by deadline between their deadline and search deadline.
     * 
     * @param string
     * @return
     */
    public List findByDate( String queryDeadline );

}