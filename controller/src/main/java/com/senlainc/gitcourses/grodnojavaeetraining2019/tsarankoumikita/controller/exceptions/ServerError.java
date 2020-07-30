package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.exceptions;

import org.springframework.http.HttpStatus;

public class ServerError {

    private HttpStatus httpStatus;

    private String message;

    private String exceptionDetails;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionDetails() {
        return exceptionDetails;
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }
}
