package br.com.amcom.laa.exception;

import br.com.amcom.laa.domain.Error;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.Response;

public class InvalidDateException extends ResponseException {

    private static final long serialVersionUID = 1L;

    private static final String INVALID_DATE = "Invalid date";

    public InvalidDateException() {
        super(INVALID_DATE,
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(new Error(INVALID_DATE)).
                        build());
    }

    public InvalidDateException(Throwable throwable) {
        super(INVALID_DATE,
                Response.status(Response.Status.BAD_REQUEST)
                .entity(new Error(INVALID_DATE, ExceptionUtils.getRootCauseMessage(throwable)))
                .build());
    }
}
