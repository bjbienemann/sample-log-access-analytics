package br.com.amcom.laa.domain;

public class Error {

    private String message;

    private String rootCauseMessage;

    public Error(String message) {
        this.message = message;
    }

    public Error(String message, String exception) {
        this.message = message;
        this.rootCauseMessage = exception;
    }

    public String getMessage() {
        return message;
    }

    public String getRootCauseMessage() {
        return rootCauseMessage;
    }
}
