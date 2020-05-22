package com.nsu.photo_anthropology.structure_entities;

public class Group {

    private int id;
    private String groupName;
    private String groupQuestion;

    public Group(int id, String groupName, String groupQuestion) {
        this.id = id;
        this.groupName = groupName;
        this.groupQuestion = groupQuestion;
    }

    public Group(String groupName, String groupQuestion) {
        this.groupName = groupName;
        this.groupQuestion = groupQuestion;
    }

    public int getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupQuestion() {
        return groupQuestion;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupQuestion='" + groupQuestion + '\'' +
                '}';
    }
}
