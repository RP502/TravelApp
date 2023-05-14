package com.example.travelapp.model;

public class Comment {
    int id;
    int cm_reply_id;
    int 	cm_user_id;
    int 	cm_article_id;
    int 	cm_hotel_id;
    int 	cm_tour_id;
    String cm_content;
    String cm_images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCm_reply_id() {
        return cm_reply_id;
    }

    public void setCm_reply_id(int cm_reply_id) {
        this.cm_reply_id = cm_reply_id;
    }

    public int getCm_user_id() {
        return cm_user_id;
    }

    public void setCm_user_id(int cm_user_id) {
        this.cm_user_id = cm_user_id;
    }

    public int getCm_article_id() {
        return cm_article_id;
    }

    public void setCm_article_id(int cm_article_id) {
        this.cm_article_id = cm_article_id;
    }

    public int getCm_hotel_id() {
        return cm_hotel_id;
    }

    public void setCm_hotel_id(int cm_hotel_id) {
        this.cm_hotel_id = cm_hotel_id;
    }

    public int getCm_tour_id() {
        return cm_tour_id;
    }

    public void setCm_tour_id(int cm_tour_id) {
        this.cm_tour_id = cm_tour_id;
    }

    public String getCm_content() {
        return cm_content;
    }

    public void setCm_content(String cm_content) {
        this.cm_content = cm_content;
    }

    public String getCm_images() {
        return cm_images;
    }

    public void setCm_images(String cm_images) {
        this.cm_images = cm_images;
    }
}
