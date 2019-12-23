package br.com.amcom.laa.service;

import br.com.amcom.laa.connection.EsConnection;
import br.com.amcom.laa.domain.Log;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;

import java.util.HashMap;
import java.util.Map;

public class InjestService extends EsConnection {

    private static final Logger LOGGER = LogManager.getLogger(InjestService.class);

    public IndexResponse newIndexRequest(Log log) throws ElasticSearchClientException {
        IndexRequest request = new IndexRequest(INDEX);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("url", log.getUrl());
        dataMap.put("dateTime", log.getDateTime());
        dataMap.put("uuid", log.getUuid());
        dataMap.put("regionCode", log.getRegionCode());

        request.source(dataMap);

        try {
            return openConnection().index(request, RequestOptions.DEFAULT);
        } catch(ElasticsearchException e) {
            LOGGER.error(e.getDetailedMessage());
            throw new ElasticSearchClientException(e);
        } catch (java.io.IOException ex){
            LOGGER.error(ex.getLocalizedMessage());
            throw new ElasticSearchClientException(ex);
        } finally {
            closeConnection();
        }
    }
}
