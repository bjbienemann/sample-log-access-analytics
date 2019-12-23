package br.com.amcom.laa.exception;

import javax.ws.rs.core.Response;

public class ResponseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final Response response;

    public ResponseException(Response response) {
        this.response = response;
    }

    public ResponseException(String message, Response response) {
        super(message);
        this.response = response;
    }

    public ResponseException(String message, Throwable cause, Response response) {
        super(message, cause);
        this.response = response;
    }

    public ResponseException(Throwable cause, Response response) {
        super(cause);
        this.response = response;
    }

    public ResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Response response) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

}
