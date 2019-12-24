package br.com.amcom.laa.application;

import br.com.amcom.laa.connection.EsConnection;
import br.com.amcom.laa.constants.EsConstants;
import br.com.amcom.laa.service.HealthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationPath("/api")
public class ApiApplication extends Application {

    private static final Logger LOGGER = LogManager.getLogger(ApiApplication.class);

    public ApiApplication() {
        super();

        loadEsConnectionProperties();
        HealthService healthService = new HealthService();
        healthService.createIndex();
    }

    private void loadEsConnectionProperties() {
        Properties esConnectionProperties = new Properties();
        try {
            InputStream inputStream = getClass().getResourceAsStream(EsConstants.ES_CONNECTION_PROPERTIES);
            esConnectionProperties.load(inputStream);
            System.setProperty(EsConstants.ES_HOST, esConnectionProperties.getProperty(EsConstants.ES_HOST));
            System.setProperty(EsConstants.ES_PORT_ONE, esConnectionProperties.getProperty(EsConstants.ES_PORT_ONE));
            System.setProperty(EsConstants.ES_PORT_TWO, esConnectionProperties.getProperty(EsConstants.ES_PORT_TWO));
            System.setProperty(EsConstants.ES_SCHEME, esConnectionProperties.getProperty(EsConstants.ES_SCHEME));
            System.setProperty(EsConstants.ES_INDEX, esConnectionProperties.getProperty(EsConstants.ES_INDEX));
        } catch (IOException e) {
            LOGGER.error(e);
        }

    }
}
