package fr.todooz.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import fr.todooz.service.TaskService;
import fr.todooz.service.TaskServiceImpl;
import fr.todooz.service.TaskServiceJPAImpl;

public class MyApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(TaskServiceJPAImpl.class).to(TaskService.class);
	}

}
