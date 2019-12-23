package br.com.amcom.laa.test;

import br.com.amcom.laa.resource.LaaaResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;


public class HealthTest extends JerseyTest {

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(LaaaResource.class);
    }

    @Test
    public void getHealthTest() {
        Response response = target("laaa/health").request().get();
        Assert.assertEquals("all is good 200", Response.Status.OK, response.getStatus());
    }
}
