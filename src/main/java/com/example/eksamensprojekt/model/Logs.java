package com.example.eksamensprojekt.model;

//Model class for all logs that will be used/shown in the app. Multiple constructors based on which data is available from Firestore
public class Logs {

    private String headline;
    private String description;
    private String image;
    private String lat;
    private String lon;
    private String date;
    private String id;
    private Boolean placeholder;

    public Logs(String headline, String description, String date, String id) {
        this.headline = headline;
        this.description = description;
        this.date = date;
        this.id = id;
    }

    public Logs(String headline, String description, String image, String date, String id) {
        this.headline = headline;
        this.description = description;
        this.image = image;
        this.date = date;
        this.id = id;
    }

    public Logs(String headline, String description, String lat, String lon, String date, String id) {
        this.headline = headline;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
        this.id = id;
    }

    public Logs(String headline, String description, String image, String lat, String lon, String date, String id) {
        this.headline = headline;
        this.description = description;
        this.image = image;
        this.lat = lat;
        this.lon = lon;
        this.date = date;
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
