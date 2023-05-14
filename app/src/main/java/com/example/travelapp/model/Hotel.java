package com.example.travelapp.model;

import java.io.Serializable;
import java.util.Date;

public class Hotel implements Serializable {
    int id;
    String h_name;
    String h_image;
    String h_address;
      String h_phone;
    String h_price;
    String h_description;
    String h_content;
    int h_price_contact;
    int h_sale;
    int t_location_id;
    int h_service_id;
    int h_user_id;
    String 	h_anbum_image;

    public Hotel(int id, String h_name, String h_image, String h_address, String h_phone, String h_price, String h_description, String h_content, int h_price_contact, int h_sale, int t_location_id, int h_service_id, int h_user_id, String h_anbum_image) {
        this.id = id;
        this.h_name = h_name;
        this.h_image = h_image;
        this.h_address = h_address;
        this.h_phone = h_phone;
        this.h_price = h_price;
        this.h_description = h_description;
        this.h_content = h_content;
        this.h_price_contact = h_price_contact;
        this.h_sale = h_sale;
        this.t_location_id = t_location_id;
        this.h_service_id = h_service_id;
        this.h_user_id = h_user_id;
        this.h_anbum_image = h_anbum_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getH_name() {
        return h_name;
    }

    public void setH_name(String h_name) {
        this.h_name = h_name;
    }

    public String getH_image() {
        return h_image;
    }

    public void setH_image(String h_image) {
        this.h_image = h_image;
    }

    public String getH_address() {
        return h_address;
    }

    public void setH_address(String h_address) {
        this.h_address = h_address;
    }

    public String getH_phone() {
        return h_phone;
    }

    public void setH_phone(String h_phone) {
        this.h_phone = h_phone;
    }

    public String getH_price() {
        return h_price;
    }

    public void setH_price(String h_price) {
        this.h_price = h_price;
    }

    public String getH_description() {
        return h_description;
    }

    public void setH_description(String h_description) {
        this.h_description = h_description;
    }

    public String getH_content() {
        return h_content;
    }

    public void setH_content(String h_content) {
        this.h_content = h_content;
    }

    public int getH_price_contact() {
        return h_price_contact;
    }

    public void setH_price_contact(int h_price_contact) {
        this.h_price_contact = h_price_contact;
    }

    public int getH_sale() {
        return h_sale;
    }

    public void setH_sale(int h_sale) {
        this.h_sale = h_sale;
    }

    public int getT_location_id() {
        return t_location_id;
    }

    public void setT_location_id(int t_location_id) {
        this.t_location_id = t_location_id;
    }

    public int getH_service_id() {
        return h_service_id;
    }

    public void setH_service_id(int h_service_id) {
        this.h_service_id = h_service_id;
    }

    public int getH_user_id() {
        return h_user_id;
    }

    public void setH_user_id(int h_user_id) {
        this.h_user_id = h_user_id;
    }

    public String getH_anbum_image() {
        return h_anbum_image;
    }

    public void setH_anbum_image(String h_anbum_image) {
        this.h_anbum_image = h_anbum_image;
    }
}
