package com.nsu.photo_anthropology.structure_entities;

public class Tag {
    private int id;
    private String tag_name;
    private String groupName;

    public Tag(int id, String tag_name, String groupName) {
        this.id = id;
        this.tag_name = tag_name;
        this.groupName = groupName;
    }

    public Tag(String tag_name, String groupName) {
        this.tag_name = tag_name;
        this.groupName = groupName;
    }

    public int getId() {
        return id;
    }

    public String getTagName() {
        return tag_name;
    }

    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag_name='" + tag_name + '\'' +
                ", groupName=" + groupName +
                '}';
    }
}
