package com.nsu.PhotoAnthropology.StructureClasses;

public class File {

    private int id;
    private String fileName;
    private String columnNames;

    public File(int id, String fileName, String columnNames) {
        this.id = id;
        this.fileName = fileName;
        this.columnNames = columnNames;
    }

    public File(String fileName, String columnNames) {
        this.fileName = fileName;
        this.columnNames = columnNames;
    }

    public String getFile_name() {
        return fileName;
    }

    public String getColumnNames() {
        return columnNames;
    }

    public int getId() {
        return id;
    }
}
