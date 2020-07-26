package com.example.eksamensprojekt.model;

//Model class for how the markers used in MapsActivity is to be created
public class Marker {

    private Double longtitude;
    private Double latitude;

    public Marker(Double longtitude, Double latitude) {
        this.longtitude = longtitude;
        this.latitude = latitude;
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
}