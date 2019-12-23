package br.com.amcom.laa.exception;

import br.com.amcom.laa.domain.Error;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.Response;

public class ElasticSearchClientException extends ResponseException {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_WHILE_QUERYING = "Error while querying";

    public ElasticSearchClientException(String message) {
        super(ERROR_WHILE_QUERYING,
                Response.serverError()
                .entity(new Error(ERROR_WHILE_QUERYING))
                .build());
    }

    public ElasticSearchClientException(Throwable throwable) {

        super(ERROR_WHILE_QUERYING,
                throwable,
                Response.serverError()
                        .entity(new Error(ERROR_WHILE_QUERYING, ExceptionUtils.getRootCauseMessage(throwable)))
                        .build());
    }

}
