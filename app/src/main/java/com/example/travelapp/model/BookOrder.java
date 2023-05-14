package com.example.travelapp.model;

import java.util.List;

public class BookOrder {
    int id;
    int b_tour_id;
    int b_user_id;
    String b_name;
    String b_email;
    String b_phone;
    String b_address;
    String b_note;
    String b_number_adults;
    String b_price_adults;
    List<Item> item;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getB_tour_id() {
        return b_tour_id;
    }

    public void setB_tour_id(int b_tour_id) {
        this.b_tour_id = b_tour_id;
    }

    public int getB_user_id() {
        return b_user_id;
    }

    public void setB_user_id(int b_user_id) {
        this.b_user_id = b_user_id;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getB_email() {
        return b_email;
    }

    public void setB_email(String b_email) {
        this.b_email = b_email;
    }

    public String getB_phone() {
        return b_phone;
    }

    public void setB_phone(String b_phone) {
        this.b_phone = b_phone;
    }

    public String getB_address() {
        return b_address;
    }

    public void setB_address(String b_address) {
        this.b_address = b_address;
    }

    public String getB_note() {
        return b_note;
    }

    public void setB_note(String b_note) {
        this.b_note = b_note;
    }

    public String getB_number_adults() {
        return b_number_adults;
    }

    public void setB_number_adults(String b_number_adults) {
        this.b_number_adults = b_number_adults;
    }

    public String getB_price_adults() {
        return b_price_adults;
    }

    public void setB_price_adults(String b_price_adults) {
        this.b_price_adults = b_price_adults;
    }

    public List<Item> getItem() {
        return item;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }
}
