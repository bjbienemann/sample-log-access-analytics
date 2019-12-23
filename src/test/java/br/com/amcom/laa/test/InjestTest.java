package br.com.amcom.laa.test;

import br.com.amcom.laa.domain.Log;
import br.com.amcom.laa.service.InjestService;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class InjestTest {

    private InjestService injestService;

    @Before
    public void init() {
        this.injestService = new InjestService();
    }

    @Test
    public void newIndexText() {
        Date dateTime = new Date(1037825323957L);

        Log log = new Log();
        log.setUrl("/pets/exotic/cats/10");
        log.setDateTime(dateTime);
        log.setUuid("5b019db5-b3d0-46d2-9963-437860af707f");
        log.setRegionCode(1);

        IndexResponse response = injestService.newIndexRequest(log);

        assertThat(response).isNotNull();
        assertThat(response.getResult().getLowercase()).isIn("created", "updated");
    }
}
