package com.example.travelapp.model;

import java.util.List;

public class BookOrderModel {
    boolean success;
    String message;
    List<BookOrder> result;

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

    public List<BookOrder> getResult() {
        return result;
    }

    public void setResult(List<BookOrder> result) {
        this.result = result;
    }
}
