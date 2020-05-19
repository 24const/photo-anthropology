package com.nsu.photo_anthropology.structure_classes;

public class Tag {
    private int id;
    private String tag_name;
    private int group_id;

    public Tag(int id, String tag_name, int group_id) {
        this.id = id;
        this.tag_name = tag_name;
        this.group_id = group_id;
    }

    public Tag(String tag_name, int group_id) {
        this.tag_name = tag_name;
        this.group_id = group_id;
    }

    public int getId() {
        return id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public int getGroup_id() {
        return group_id;
    }
}
