package br.com.amcom.laa.test;

import br.com.amcom.laa.mock.LogMock;
import br.com.amcom.laa.service.InjestService;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InjestTest {

    private static final String CREATED = "created";
    private static final String UPDATED = "updated";

    private InjestService injestService;
    private LogMock logMock;

    @Before
    public void init() {
        this.injestService = new InjestService();
        this.logMock = new LogMock();
    }

    @Test
    public void newIndex1() {
        IndexResponse response = injestService.newIndexRequest(logMock.createUrl1());

        assertThat(response).isNotNull();
        assertThat(response.getResult().getLowercase()).isIn(CREATED, UPDATED);
    }

    @Test
    public void newIndex2() {
        IndexResponse response = injestService.newIndexRequest(logMock.createUrl2());

        assertThat(response).isNotNull();
        assertThat(response.getResult().getLowercase()).isIn(CREATED, UPDATED);
    }

    @Test
    public void newIndex3() {
        IndexResponse response = injestService.newIndexRequest(logMock.createUrl3());

        assertThat(response).isNotNull();
        assertThat(response.getResult().getLowercase()).isIn(CREATED, UPDATED);
    }

    @Test @Ignore
    // insert data mocked
    public void newRandonIndex() {
        for (int i = 0; i < 100; i++) {
            injestService.newIndexRequest(logMock.createUrl());
        }
    }
}
