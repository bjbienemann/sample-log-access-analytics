package br.com.amcom.laa.connection;

import br.com.amcom.laa.constants.EsConstants;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsConnection {

    protected static final int DURATION = 60;

    private RestHighLevelClient restHighLevelClient;

    protected RestHighLevelClient openConnection() {
        if(restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(getHost(), getPortOne(), getScheme()),
                            new HttpHost(getHost(), getPortTwo(), getScheme())));
        }

        return restHighLevelClient;
    }

    protected void closeConnection() {
        try {
            if (restHighLevelClient != null) {
                restHighLevelClient.close();
                restHighLevelClient = null;
            }
        } catch (IOException e) {
            throw new ElasticSearchClientException(e);
        }
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    private String getHost() {
        return System.getProperty(EsConstants.ES_HOST);
    }

    private Integer getPortOne() {
        String property = System.getProperty(EsConstants.ES_PORT_ONE);
        return Integer.valueOf(property);
    }

    private Integer getPortTwo() {
        String property = System.getProperty(EsConstants.ES_PORT_TWO);
        return Integer.valueOf(property);
    }

    private String getScheme() {
        return System.getProperty(EsConstants.ES_SCHEME);
    }

    protected String getIndex() {
        return System.getProperty(EsConstants.ES_INDEX);
    }
}
