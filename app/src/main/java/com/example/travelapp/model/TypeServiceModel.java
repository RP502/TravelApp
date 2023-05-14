package com.example.travelapp.model;

import java.util.List;

public class TypeServiceModel {
    boolean success;
    String message;
    List<TypeService> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TypeService> getResult() {
        return result;
    }

    public void setResult(List<TypeService> result) {
        this.result = result;
    }
}
