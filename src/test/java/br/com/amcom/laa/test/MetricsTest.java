package br.com.amcom.laa.test;

import br.com.amcom.laa.service.MetricsService;
import org.assertj.core.api.Assertions;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Before;
import org.junit.Test;

public class MetricsTest {

    private MetricsService metricsService;

    @Before
    public void init() {
        metricsService = new MetricsService();
    }

    @Test
    public void top3WorldTest() {
        SearchResponse searchResponse = metricsService.searchTop3World();

        Assertions.assertThat(searchResponse).isNotNull();
    }
}
