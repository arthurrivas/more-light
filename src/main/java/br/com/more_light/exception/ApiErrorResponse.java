package br.com.more_light.exception;

import java.util.List;

public class ApiErrorResponse {
    private List<String> errors;

    public ApiErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}

