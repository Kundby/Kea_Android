package com.example.kea_week8_mynotebook.model;

public class Note {

    private String headline;
    private String body;

    public Note(String headline, String body) {
        this.headline = headline;
        this.body = body;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
