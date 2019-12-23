package br.com.amcom.laa.service;

import br.com.amcom.laa.connection.EsConnection;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HealthService extends EsConnection {

    private static final Logger LOGGER = LogManager.getLogger(HealthService.class);

    public ClusterHealthResponse getHealth() {
        ClusterHealthRequest request = new ClusterHealthRequest(INDEX);
        request.timeout(TimeValue.timeValueSeconds(DURATION));
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

    public void createIndex() {
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX);
            boolean exists = openConnection().indices().exists(getIndexRequest, RequestOptions.DEFAULT);

            if (!exists) {
                LOGGER.info("Creating index ".concat(INDEX));
                CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX);
                createIndexRequest.mapping(getPropertiesMapping());

                openConnection().indices().create(createIndexRequest, RequestOptions.DEFAULT);
                LOGGER.info("Created index ".concat(INDEX));
            }
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new ElasticSearchClientException(e);
        } finally {
            closeConnection();
        }
    }

    public Map<String, Object> getPropertiesMapping() {
        Map<String, Object> url = new HashMap<>();
        url.put("type", "keyword");

        Map<String, Object> dateTime = new HashMap<>();
        dateTime.put("type", "date");
        dateTime.put("format", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Map<String, Object> properties = new HashMap<>();
        properties.put("url", url);
        properties.put("dateTime", dateTime);

        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);

        return mapping;
    }

}
