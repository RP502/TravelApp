package com.example.travelapp.model;

public class Item {
    int id;
    String t_title;
    int b_number_adults;
    int b_price_adults;
    String t_image;

    public String getT_image() {
        return t_image;
    }

    public void setT_image(String t_image) {
        this.t_image = t_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getT_title() {
        return t_title;
    }

    public void setT_title(String t_title) {
        this.t_title = t_title;
    }

    public int getB_number_adults() {
        return b_number_adults;
    }

    public void setB_number_adults(int b_number_adults) {
        this.b_number_adults = b_number_adults;
    }

    public int getB_price_adults() {
        return b_price_adults;
    }

    public void setB_price_adults(int b_price_adults) {
        this.b_price_adults = b_price_adults;
    }
}
