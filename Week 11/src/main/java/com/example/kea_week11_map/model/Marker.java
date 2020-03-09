package com.example.kea_week11_map.model;

public class Marker {

    private Double longtitude;
    private Double latitude;
    private String title;

    public Marker(Double longtitude, Double latitude, String title) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.title = title;
    }

    public Double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(Double longtitude) {
        this.longtitude = longtitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
