package com.example.travelapp.model;

import java.io.Serializable;
import java.util.Date;

public class NewTour implements Serializable {
    int id;
    String t_title;
    String t_journeys;
    String t_schedule;
    String t_image;
    String t_move_method;
    Date t_start_date;
    Date t_end_date;
    int t_number_guests;
    String t_price_adults;
    String t_price_child;
    int t_sale;
    int t_view;
    String t_description;
    String t_content;
    int t_follow;
    int t_number_registered;
    int t_location_id;
    int t_service_id;
    String 	t_anbum_image;

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

    public String getT_journeys() {
        return t_journeys;
    }

    public void setT_journeys(String t_journeys) {
        this.t_journeys = t_journeys;
    }

    public String getT_schedule() {
        return t_schedule;
    }

    public void setT_schedule(String t_schedule) {
        this.t_schedule = t_schedule;
    }

    public String getT_image() {
        return t_image;
    }

    public void setT_image(String t_image) {
        this.t_image = t_image;
    }

    public String getT_move_method() {
        return t_move_method;
    }

    public void setT_move_method(String t_move_method) {
        this.t_move_method = t_move_method;
    }

    public Date getT_start_date() {
        return t_start_date;
    }

    public void setT_start_date(Date t_start_date) {
        this.t_start_date = t_start_date;
    }

    public Date getT_end_date() {
        return t_end_date;
    }

    public void setT_end_date(Date t_end_date) {
        this.t_end_date = t_end_date;
    }

    public int getT_number_guests() {
        return t_number_guests;
    }

    public void setT_number_guests(int t_number_guests) {
        this.t_number_guests = t_number_guests;
    }

    public String getT_price_adults() {
        return t_price_adults;
    }

    public void setT_price_adults(String t_price_adults) {
        this.t_price_adults = t_price_adults;
    }

    public String getT_price_child() {
        return t_price_child;
    }

    public void setT_price_child(String t_price_child) {
        this.t_price_child = t_price_child;
    }

    public int getT_sale() {
        return t_sale;
    }

    public void setT_sale(int t_sale) {
        this.t_sale = t_sale;
    }

    public int getT_view() {
        return t_view;
    }

    public void setT_view(int t_view) {
        this.t_view = t_view;
    }

    public String getT_description() {
        return t_description;
    }

    public void setT_description(String t_description) {
        this.t_description = t_description;
    }

    public String getT_content() {
        return t_content;
    }

    public void setT_content(String t_content) {
        this.t_content = t_content;
    }

    public int getT_follow() {
        return t_follow;
    }

    public void setT_follow(int t_follow) {
        this.t_follow = t_follow;
    }

    public int getT_number_registered() {
        return t_number_registered;
    }

    public void setT_number_registered(int t_number_registered) {
        this.t_number_registered = t_number_registered;
    }

    public int getT_location_id() {
        return t_location_id;
    }

    public void setT_location_id(int t_location_id) {
        this.t_location_id = t_location_id;
    }

    public int getT_service_id() {
        return t_service_id;
    }

    public void setT_service_id(int t_service_id) {
        this.t_service_id = t_service_id;
    }

    public String getT_anbum_image() {
        return t_anbum_image;
    }

    public void setT_anbum_image(String t_anbum_image) {
        this.t_anbum_image = t_anbum_image;
    }
}
