package br.com.amcom.laa.service;

import br.com.amcom.laa.connection.EsConnection;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;

public class HealthService extends EsConnection {

    private static final Logger LOGGER = LogManager.getLogger(MetricsService.class);

    public ClusterHealthResponse getHealth() throws ElasticSearchClientException {
        ClusterHealthRequest request = new ClusterHealthRequest(INDEX);
        request.timeout(TimeValue.timeValueSeconds(50));
        request.waitForYellowStatus();
        try {
            return openConnection().cluster().health(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new ElasticSearchClientException(e);
        } finally {
            closeConnection();
        }
    }

}
