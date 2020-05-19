package com.nsu.photo_anthropology.structure_classes;

import org.json.simple.JSONArray;

import java.nio.file.Path;

public class File {

    private int id;
    private String fileName;
    private JSONArray columnNames;

    public File(int id, String fileName, JSONArray columnNames) {
        this.id = id;
        this.fileName = fileName;
        this.columnNames = columnNames;
    }

    public File(String fileName, JSONArray columnNames) {
        this.fileName = fileName;
        this.columnNames = columnNames;
    }

    public String getFileName() {
        return fileName;
    }

    public JSONArray getColumnNames() {
        return columnNames;
    }

    public int getId() {
        return id;
    }
}
