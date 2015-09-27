package fr.todooz.rest;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import fr.todooz.service.TaskService;
import fr.todooz.service.TaskServiceImpl;

public class MyApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(TaskServiceImpl.class).to(TaskService.class);
	}

}
