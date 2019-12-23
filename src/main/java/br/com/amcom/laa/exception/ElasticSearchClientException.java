package br.com.amcom.laa.exception;

import br.com.amcom.laa.domain.Error;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.elasticsearch.action.search.SearchResponse;

import javax.ws.rs.core.Response;

public class ElasticSearchClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Response response;

    public ElasticSearchClientException(String message) {
        super(message);
    }

    public ElasticSearchClientException(Throwable throwable) {
        super(throwable.getLocalizedMessage());

        createError(throwable);
    }
    public ElasticSearchClientException(String message, Throwable throwable) {
        super(message);

        createError(throwable);
    }

    private void createError(Throwable throwable) {
        Error error = new Error();
        error.setMessage("Rrror while querying");
        error.setException(ExceptionUtils.getRootCauseMessage(throwable));

        response = Response.serverError().entity(error).build();
    }

    public Response getResponse() {
        return response;
    }

}
