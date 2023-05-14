package com.example.travelapp.model;

public class Cart {
    int id;
    String cart_name;
    long cart_price;
    int cart_quantity;
    String img_cart;

    public Cart() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCart_name() {
        return cart_name;
    }

    public void setCart_name(String cart_name) {
        this.cart_name = cart_name;
    }

    public long getCart_price() {
        return cart_price;
    }

    public void setCart_price(long cart_price) {
        this.cart_price = cart_price;
    }

    public int getCart_quantity() {
        return cart_quantity;
    }

    public void setCart_quantity(int cart_quantity) {
        this.cart_quantity = cart_quantity;
    }

    public String getImg_cart() {
        return img_cart;
    }

    public void setImg_cart(String img_cart) {
        this.img_cart = img_cart;
    }
}
