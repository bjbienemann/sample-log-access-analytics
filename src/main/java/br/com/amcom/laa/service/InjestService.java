package br.com.amcom.laa.service;

import br.com.amcom.laa.connection.EsConnection;
import br.com.amcom.laa.domain.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InjestService extends EsConnection {

    private static final Logger LOGGER = LogManager.getLogger(InjestService.class);

    public IndexResponse newIndexRequest(Log log) {
        IndexRequest request = new IndexRequest("laa");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("url", log.getUrl());
        dataMap.put("dateTime", log.getDateTime());
        dataMap.put("uuid", log.getUuid());
        dataMap.put("regionCode", log.getRegionCode());

        request.source(dataMap);

        try {
            return openConnection().index(request, RequestOptions.DEFAULT);
        } catch(ElasticsearchException e) {
            LOGGER.error(e.getDetailedMessage());
        } catch (java.io.IOException ex){
            LOGGER.error(ex.getLocalizedMessage());
        } finally {
            try {
                closeConnection();
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }

        return null;
    }
}
