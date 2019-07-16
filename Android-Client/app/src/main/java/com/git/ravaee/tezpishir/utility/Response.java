package com.git.ravaee.tezpishir.utility;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    public Response(){}

    public Response(String message, List<String> errors, boolean success) {
        this.message = message;
        this.errors = errors;
        this.success = success;
    }

    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private List<String> errors;

    @SerializedName("success")
    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
