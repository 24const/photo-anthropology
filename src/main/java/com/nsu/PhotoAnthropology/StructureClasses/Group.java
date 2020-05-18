package com.nsu.PhotoAnthropology.StructureClasses;

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
        this.id = id;
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
}
