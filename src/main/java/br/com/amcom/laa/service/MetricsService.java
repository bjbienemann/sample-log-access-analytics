package br.com.amcom.laa.service;

import br.com.amcom.laa.connection.EsConnection;
import br.com.amcom.laa.domain.Access;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MetricsService extends EsConnection {

    private static final Logger LOGGER = LogManager.getLogger(MetricsService.class);

    private static final String TOP_3_URLS = "top3_urls";

    public List<Access> getTop3World() throws ElasticSearchClientException {
        SearchResponse searchResponse = searchTop3World();

        return aggsToAccess(searchResponse);
    }

    public SearchResponse searchTop3World() {
        TermsAggregationBuilder termsAggregationBuilder = getTermsAggregationBuilder(3, Boolean.FALSE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(0);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        searchRequest.source(searchSourceBuilder);

        try {
            return openConnection().search(searchRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new ElasticSearchClientException(e);
        } finally {
            closeConnection();
        }
    }

    public List<Access> getTop3Region(Integer regionCode) throws ElasticSearchClientException {
        SearchResponse searchResponse = searchTop3Region(regionCode);

        return aggsToAccess(searchResponse);
    }

    private SearchResponse searchTop3Region(Integer regionCode) {
        TermsAggregationBuilder termsAggregationBuilder = getTermsAggregationBuilder(3, Boolean.FALSE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("regionCode", regionCode));
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(0);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        searchRequest.source(searchSourceBuilder);

        try {
            return openConnection().search(searchRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new ElasticSearchClientException(e);
        } finally {
            closeConnection();
        }
    }

    public List<Access> getLessWorld() {
        SearchResponse searchResponse = searchLessWorld();

        return aggsToAccess(searchResponse);
    }

    private SearchResponse searchLessWorld() throws ElasticSearchClientException {
        TermsAggregationBuilder termsAggregationBuilder = getTermsAggregationBuilder(1, Boolean.TRUE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(0);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX);
        searchRequest.source(searchSourceBuilder);

        try {
            return openConnection().search(searchRequest, RequestOptions.DEFAULT);
        } catch (java.io.IOException e){
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new ElasticSearchClientException(e);
        } finally {
            closeConnection();
        }
    }

    private List<Access> aggsToAccess(SearchResponse searchResponse) {
        Map<String, Aggregation> aggregationMap = searchResponse.getAggregations().getAsMap();
        if (aggregationMap.containsKey(MetricsService.TOP_3_URLS)) {
            List<? extends Terms.Bucket> top3World = ((Terms) aggregationMap.get(MetricsService.TOP_3_URLS)).getBuckets();
            return top3World.stream().map(this::bucketToAccess).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    private Access bucketToAccess(Terms.Bucket bucket) throws ElasticSearchClientException {
        Access access = new Access();
        access.setUrl(bucket.getKeyAsString());
        access.setCount(bucket.getDocCount());

        return access;
    }

    private TermsAggregationBuilder getTermsAggregationBuilder(Integer size, Boolean asc) {
        return AggregationBuilders.terms(TOP_3_URLS)
                .field("url.keyword")
                .size(size)
                .order(BucketOrder.aggregation("_count", asc));
    }
}
