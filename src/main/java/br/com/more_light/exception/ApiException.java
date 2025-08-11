package br.com.more_light.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}

