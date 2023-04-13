package com.restapi.qaautomation.webservices.test.exception;

public class RestAssuredException extends Exception {
    public RestAssuredException(String msg) {
        super(msg);
    }

    public RestAssuredException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
