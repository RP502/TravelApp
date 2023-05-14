package com.example.travelapp.model;

import java.util.List;

public class ArticlesModel {
    boolean success;
    String message;
    List<Articles> result;

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

    public List<Articles> getResult() {
        return result;
    }

    public void setResult(List<Articles> result) {
        this.result = result;
    }
}
