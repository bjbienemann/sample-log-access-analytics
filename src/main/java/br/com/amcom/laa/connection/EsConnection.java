package br.com.amcom.laa.connection;

import br.com.amcom.laa.exception.ElasticSearchClientException;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class EsConnection {

    private static final String HOST = "localhost";
    private static final int PORT_ONE = 9200;
    private static final int PORT_TWO = 9300;
    private static final String SCHEME = "http";
    protected static final String INDEX = "laa";

    private RestHighLevelClient restHighLevelClient;

    protected RestHighLevelClient openConnection() {
        if(restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME),
                            new HttpHost(HOST, PORT_TWO, SCHEME)));
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
}
