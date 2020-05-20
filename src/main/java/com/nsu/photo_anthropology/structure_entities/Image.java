package com.nsu.photo_anthropology.structure_entities;

import org.json.simple.JSONArray;

public class Image {

    private int id;
    private String imagePath;
    private String fileName;
    private JSONArray otherInformation;

    public Image(int id, String fileName, String imagePath, JSONArray otherInformation) {
        this.id = id;
        this.fileName = fileName;
        this.imagePath = imagePath;
        this.otherInformation = otherInformation;
    }

    public Image(String fileName, String imagePath, JSONArray otherInformation) {
        this.fileName = fileName;
        this.imagePath = imagePath;
        this.otherInformation = otherInformation;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public JSONArray getOtherInformation() {
        return otherInformation;
    }

    public String getFileName() {
        return fileName;
    }
}
