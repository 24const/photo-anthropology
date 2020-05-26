package com.nsu.photo_anthropology.structure_entities;

public class Tag {
    private int id;
    private String tagName;
    private String groupName;

    public Tag(int id, String tagName, String groupName) {
        this.id = id;
        this.tagName = tagName;
        this.groupName = groupName;
    }

    public Tag(String tagName, String groupName) {
        this.tagName = tagName;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public String getTagName() {
        return tagName;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag_name='" + tagName + '\'' +
                ", groupName=" + groupName +
                '}';
    }
}
