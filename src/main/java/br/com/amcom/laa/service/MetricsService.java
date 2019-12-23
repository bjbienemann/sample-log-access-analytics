package br.com.amcom.laa.service;

import br.com.amcom.laa.connection.EsConnection;
import br.com.amcom.laa.domain.Access;
import br.com.amcom.laa.exception.ElasticSearchClientException;
import br.com.amcom.laa.exception.InvalidDateException;
import br.com.amcom.laa.exception.NoRecordsFoundExcepetion;
import br.com.amcom.laa.util.DateUtil;
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
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogramInterval;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.histogram.ParsedDateHistogram;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MetricsService extends EsConnection {

    private static final Logger LOGGER = LogManager.getLogger(MetricsService.class);

    private static final String TOP_3_URLS = "top3_urls";
    private static final String URL = "url";
    private static final String DATE_TIME = "dateTime";
    private static final String REGION_CODE = "regionCode";
    private static final String COUNT = "_count";
    private static final int ZERO = 0;

    public List<Access> getTop3World() {
        SearchResponse searchResponse = searchTop3World();

        return aggsTermsToAccess(searchResponse);
    }

    public SearchResponse searchTop3World() {
        TermsAggregationBuilder termsAggregationBuilder = getTermsAggregationBuilder(3, Boolean.FALSE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.from(ZERO);
        searchSourceBuilder.size(ZERO);
        searchSourceBuilder.timeout(new TimeValue(DURATION, TimeUnit.SECONDS));

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

    public List<Access> getTop3Region(Integer regionCode) {
        SearchResponse searchResponse = searchTop3Region(regionCode);

        return aggsTermsToAccess(searchResponse);
    }

    private SearchResponse searchTop3Region(Integer regionCode) {
        TermsAggregationBuilder termsAggregationBuilder = getTermsAggregationBuilder(3, Boolean.FALSE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery(REGION_CODE, regionCode));
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.from(ZERO);
        searchSourceBuilder.size(ZERO);
        searchSourceBuilder.timeout(new TimeValue(DURATION, TimeUnit.SECONDS));

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

        return aggsTermsToAccess(searchResponse);
    }

    private SearchResponse searchLessWorld() {
        TermsAggregationBuilder termsAggregationBuilder = getTermsAggregationBuilder(1, Boolean.TRUE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.from(ZERO);
        searchSourceBuilder.size(ZERO);
        searchSourceBuilder.timeout(new TimeValue(DURATION, TimeUnit.SECONDS));

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

    public List<Access> getTop3Period(Integer day, Integer week, Integer year) {
        if (Objects.isNull(day) || Objects.isNull(week) || Objects.isNull(year)) {
            throw new InvalidDateException();
        }

        String strDate = getStrDate(day, week, year);

        if (!DateUtil.isValidDate(strDate)) {
            throw new InvalidDateException();
        }

        SearchResponse searchResponse = searchTop3Period(strDate);

        return aggsTermsToAccess(searchResponse);
    }

    private String getStrDate(Integer day, Integer week, Integer year) {
        return new StringBuilder()
                    .append(day)
                    .append("/")
                    .append(week)
                    .append("/")
                    .append(year)
                    .toString();
    }

    private SearchResponse searchTop3Period(String strDate) {
        TermsAggregationBuilder termsAggregationBuilder = getTermsAggregationBuilder(3, Boolean.FALSE);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery(DATE_TIME)
                .gte(strDate)
                .lte(strDate)
                .format(DateUtil.DD_MM_YYYY));

        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchSourceBuilder.from(ZERO);
        searchSourceBuilder.size(ZERO);
        searchSourceBuilder.timeout(new TimeValue(DURATION, TimeUnit.SECONDS));

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

    private List<Access> aggsTermsToAccess(SearchResponse searchResponse) {
        if(Objects.isNull(searchResponse) || Objects.isNull(searchResponse.getAggregations())) {
            throw new NoRecordsFoundExcepetion();
        }

        Map<String, Aggregation> aggregationMap = searchResponse.getAggregations().getAsMap();
        if (!aggregationMap.containsKey(MetricsService.TOP_3_URLS)) {
            throw new NoRecordsFoundExcepetion();
        }

        List<? extends Terms.Bucket> top3World = ((Terms) aggregationMap.get(MetricsService.TOP_3_URLS)).getBuckets();
        return top3World.stream().map(this::termsToAccess).collect(Collectors.toList());
    }

    private Access termsToAccess(Terms.Bucket bucket) {
        return new Access(bucket.getKeyAsString(), bucket.getDocCount());
    }

    private TermsAggregationBuilder getTermsAggregationBuilder(Integer size, Boolean asc) {
        return AggregationBuilders.terms(TOP_3_URLS)
                .field(URL)
                .size(size)
                .order(BucketOrder.aggregation(COUNT, asc));
    }

    public List<Access> getMinuteMoreAccess() {
        SearchResponse searchResponse = searchMinuteMoreAccess();

        return aggsHistogramToAccess(searchResponse);
    }

    private SearchResponse searchMinuteMoreAccess() {
        DateHistogramAggregationBuilder dateHistogramAggregationBuilder = getDateHistogramAggregationBuilder();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(dateHistogramAggregationBuilder);
        searchSourceBuilder.from(ZERO);
        searchSourceBuilder.size(ZERO);
        searchSourceBuilder.timeout(new TimeValue(DURATION, TimeUnit.SECONDS));

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

    private List<Access> aggsHistogramToAccess(SearchResponse searchResponse){
        if(Objects.isNull(searchResponse) || Objects.isNull(searchResponse.getAggregations())) {
            throw new NoRecordsFoundExcepetion();
        }
        ParsedDateHistogram aggs = searchResponse.getAggregations().get(TOP_3_URLS);
        if(Objects.isNull(aggs) || Objects.isNull(aggs.getBuckets())) {
            throw new NoRecordsFoundExcepetion();
        }

        return aggs.getBuckets().stream().map(this::histogramToAccess).collect(Collectors.toList());
    }

    private Access histogramToAccess(Histogram.Bucket bucket) {
        return new Access(bucket.getKeyAsString(), bucket.getDocCount());
    }

    private DateHistogramAggregationBuilder getDateHistogramAggregationBuilder() {
        return AggregationBuilders
                .dateHistogram(TOP_3_URLS)
                .field(DATE_TIME)
                .fixedInterval(DateHistogramInterval.MINUTE)
                .format("yyyy-MM-dd:HH.mm")
                .minDocCount(1)
                .order(BucketOrder.aggregation(COUNT, Boolean.FALSE));
    }
}
