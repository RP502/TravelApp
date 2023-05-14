package com.example.travelapp.utils;

import com.example.travelapp.model.Cart;
import com.example.travelapp.model.NewTour;
import com.example.travelapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://192.168.0.100:8002/TourWebsite/";//192.168.1.41 //172.20.10.2
    public static List<Cart> arrCart;
    public static List<Cart> arrGetCar = new ArrayList<>();
    public  static User user_current = new User();
    public  static NewTour tour_current = new NewTour();

}

