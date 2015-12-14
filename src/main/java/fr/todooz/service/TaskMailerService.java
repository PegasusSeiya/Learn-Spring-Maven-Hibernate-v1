package fr.todooz.service;

import fr.todooz.domain.Task;

public interface TaskMailerService {
	
	public void sendEmailWithTaskInfo(final Task task, final String changeState);
	
}
