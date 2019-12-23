package br.com.amcom.laa.exception;

import javax.ws.rs.core.Response;

public class NoRecordsFoundExcepetion extends ResponseException {

    private static final String NO_RECORDS_FOUND = "No records found";

    public NoRecordsFoundExcepetion() {
        super(NO_RECORDS_FOUND,
                Response.status(Response.Status.NOT_FOUND)
                .entity(new Error(NO_RECORDS_FOUND))
                .build());
    }


}
