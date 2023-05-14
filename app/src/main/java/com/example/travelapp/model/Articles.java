package com.example.travelapp.model;

import java.io.Serializable;

public class Articles implements Serializable {
    int id;
    String a_title;
    String a_description;
    String a_content;
    String a_avatar;
    int a_category_id;
    int a_user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getA_title() {
        return a_title;
    }

    public void setA_title(String a_title) {
        this.a_title = a_title;
    }

    public String getA_description() {
        return a_description;
    }

    public void setA_description(String a_description) {
        this.a_description = a_description;
    }

    public String getA_content() {
        return a_content;
    }

    public void setA_content(String a_content) {
        this.a_content = a_content;
    }

    public String getA_avatar() {
        return a_avatar;
    }

    public void setA_avatar(String a_avatar) {
        this.a_avatar = a_avatar;
    }

    public int getA_category_id() {
        return a_category_id;
    }

    public void setA_category_id(int a_category_id) {
        this.a_category_id = a_category_id;
    }

    public int getA_user_id() {
        return a_user_id;
    }

    public void setA_user_id(int a_user_id) {
        this.a_user_id = a_user_id;
    }
}
