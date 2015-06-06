package fr.todooz.webservice;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.todooz.service.TaskService;
import fr.todooz.util.Tasks;

@WebService
@SOAPBinding( style = SOAPBinding.Style.RPC )
public class TaskWebService extends SpringBeanAutowiringSupport {
    @Inject
    @Qualifier( "hibernate" )
    private TaskService taskService;

    public Tasks getTasks() {
        return new Tasks( taskService.findAll() );
    }

}
