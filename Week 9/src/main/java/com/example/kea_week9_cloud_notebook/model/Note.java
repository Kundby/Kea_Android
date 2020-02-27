package com.example.kea_week9_cloud_notebook.model;

public class Note {

    private String headline;
    private String body;
    private String id;

    public Note(String headline, String body){
        this.headline = headline;
        this.body = body;
    }

    public Note(String headline, String body, String id) {
        this.headline = headline;
        this.body = body;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
