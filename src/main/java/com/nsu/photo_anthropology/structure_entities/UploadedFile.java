package com.nsu.photo_anthropology.structure_entities;

import org.json.simple.JSONArray;

import java.util.List;
import java.util.Objects;

public class UploadedFile {

    private int id;
    private String fileName;
    private JSONArray columnNames;
    private List<Image> imagesInFile;

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
     * Конструктор - создание нового объекта
     *
     * @param fileName     - имя файла
     * @param columnNames  - поля, содержащиеся в файле
     * @param imagesInFile - изображения, содержащиеся в файле
     * @see UploadedFile#UploadedFile(int, String, JSONArray)
     */
    public UploadedFile(String fileName, JSONArray columnNames, List<Image> imagesInFile) {
        this.fileName = fileName;
        this.columnNames = columnNames;
        this.imagesInFile = imagesInFile;
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

    /**
     * Функция получения значения поля {@link UploadedFile#imagesInFile}
     *
     * @return Возвращает список изображений, содержащихся в файле
     */
    public List<Image> getImagesInFile() {
        return this.imagesInFile;
    }

    @Override
    public String toString() {
        return "File{" +
                "fileName='" + fileName + '\'' +
                ", columnNames=" + columnNames +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UploadedFile file = (UploadedFile) object;
        return id == file.id &&
                Objects.equals(fileName, file.fileName) &&
                Objects.equals(columnNames, file.columnNames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, columnNames);
    }
}
