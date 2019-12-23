package br.com.amcom.laa.application;

import br.com.amcom.laa.service.HealthService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApiApplication extends Application {

    public ApiApplication() {
        super();

        HealthService healthService = new HealthService();
        healthService.createIndex();
    }
}
