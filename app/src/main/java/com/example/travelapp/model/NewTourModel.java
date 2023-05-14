package com.example.travelapp.model;


import java.util.List;

public class NewTourModel {
    boolean success;
    String message;
    List<NewTour> result;

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

    public List<NewTour> getResult() {
        return result;
    }

    public void setResult(List<NewTour> result) {
        this.result = result;
    }
}
