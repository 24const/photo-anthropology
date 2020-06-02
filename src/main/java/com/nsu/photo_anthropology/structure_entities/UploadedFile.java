package com.nsu.photo_anthropology.structure_entities;

import org.json.simple.JSONArray;

public class UploadedFile {

    private int id;
    private String fileName;
    private JSONArray columnNames;

    /**
     * Конструктор - создание нового объекта
     *
     * @param id          - id файла в таблице files БД
     * @param fileName    - имя файла
     * @param columnNames - поля, содержащиеся в файле
     * @see UploadedFile#UploadedFile(String, JSONArray)
     */
    public UploadedFile(int id, String fileName, JSONArray columnNames) {
        this.id = id;
        this.fileName = fileName;
        this.columnNames = columnNames;
    }

    /**
     * Конструктор - создание нового объекта
     *
     * @param fileName    - имя файла
     * @param columnNames - поля, содержащиеся в файле
     * @see UploadedFile#UploadedFile(int, String, JSONArray)
     */
    public UploadedFile(String fileName, JSONArray columnNames) {
        this.fileName = fileName;
        this.columnNames = columnNames;
    }

    /**
     * Функция получения значения поля {@link UploadedFile#fileName}
     *
     * @return Возвращает имя файла
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Функция получения значения поля {@link UploadedFile#columnNames}
     *
     * @return Возвращает поля, содержащиеся в файле
     */
    public JSONArray getColumnNames() {
        return columnNames;
    }

    /**
     * Функция получения значения поля {@link UploadedFile#id}
     *
     * @return Возвращает id файла из таблицы files БД
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", columnNames=" + columnNames +
                '}';
    }
}
