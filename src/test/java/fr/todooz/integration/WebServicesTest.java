package fr.todooz.integration;

import org.junit.Assert;
import org.junit.Test;

import fr.todooz.integration.generated.TaskWebService;
import fr.todooz.integration.generated.TaskWebServiceService;
import fr.todooz.integration.generated.TodoozWebService;
import fr.todooz.integration.generated.TodoozWebServiceService;

public class WebServicesTest {
    /**
     * Test TodoozWebService.
     */
    @Test
    public void hi() {
        TodoozWebServiceService factory = new TodoozWebServiceService();
        TodoozWebService webService = factory.getTodoozWebServicePort();

        Assert.assertEquals( "Hello jax-ws", webService.hi( "jax-ws" ) );
    }

    /**
     * Test TaskWebService.
     */
    @Test
    public void getTasks() {
        TaskWebServiceService factory = new TaskWebServiceService();
        TaskWebService webService = factory.getTaskWebServicePort();

        Assert.assertTrue( webService.getTasks().getTasks().size() > 0 );
        Assert.assertTrue( webService.getTasks().getTasks().get( 0 ).getTitle().length() > 0 );
    }

}
