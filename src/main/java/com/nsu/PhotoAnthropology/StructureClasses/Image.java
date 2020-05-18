package com.nsu.PhotoAnthropology.StructureClasses;

public class Image {

    private int id;
    private String imagePath;
    private int fileId;
    private String otherInformation;

    public Image(int id, int fileId, String imagePath, String otherInformation) {
        this.id = id;
        this.fileId = fileId;
        this.imagePath = imagePath;
        this.otherInformation = otherInformation;
    }

    public Image(int fileId, String imagePath, String otherInformation) {
        this.fileId = fileId;
        this.imagePath = imagePath;
        this.otherInformation = otherInformation;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public int getFileId() {
        return fileId;
    }
}
