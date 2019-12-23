package br.com.amcom.laa.test;

import br.com.amcom.laa.resource.LaaaResource;
import br.com.amcom.laa.service.HealthService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;


public class HealthTest extends JerseyTest {

    private HealthService healthService;

    @Override
    public Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        return new ResourceConfig(LaaaResource.class);
    }

    @Before
    public void init() {
        healthService = new HealthService();
    }

    @Test
    public void healthTest() {
        Response response = target("laaa/health").request().get();
        Assert.assertEquals("all is good 200", Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void indexTest() {

    }
}
