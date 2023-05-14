package com.example.travelapp.model;

import java.util.List;

public class HotelModel {
    boolean success;
    String message;
    List<Hotel> result;

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

    public List<Hotel> getResult() {
        return result;
    }

    public void setResult(List<Hotel> result) {
        this.result = result;
    }
}
