package fr.todooz.integration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

/**
 * Rest WebServices
 * @author Jessica
 *
 */
public class HelloWorldResourceTest {
    @Test
    public void hi() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080").path("/rest/hi/jax-rs");

        Assert.assertEquals("Hello jax-rs", target.request(MediaType.TEXT_PLAIN_TYPE).get(String.class));
    }
}
