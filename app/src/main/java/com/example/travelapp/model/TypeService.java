package com.example.travelapp.model;

public class TypeService {
    int id;
    String nameService;
    String imgService;

    public TypeService( String nameService, String imgService) {

        this.nameService = nameService;
        this.imgService = imgService;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public String getImgService() {
        return imgService;
    }

    public void setImgService(String imgService) {
        this.imgService = imgService;
    }
}
