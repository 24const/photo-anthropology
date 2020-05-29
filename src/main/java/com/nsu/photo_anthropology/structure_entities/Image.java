package com.nsu.photo_anthropology.structure_entities;

import org.json.simple.JSONArray;

public class Image {

    private int id;
    private String imagePath;
    private String fileName;
    private JSONArray otherInformation;

    /**
     * Конструктор - создание нового объекта
     *
     * @param id               - id зображения в таблице images БД
     * @param fileName         - имя файла, в котором содержится изображение
     * @param imagePath        - ссылка на изображение
     * @param otherInformation - прочая информация об изображении в формате JSONArray
     * @see Image#Image(String, String, JSONArray)
     */
    public Image(int id, String fileName, String imagePath, JSONArray otherInformation) {
        this.id = id;
        this.fileName = fileName;
        this.imagePath = imagePath;
        this.otherInformation = otherInformation;
    }

    /**
     * Конструктор - создание нового объекта
     *
     * @param fileName         - имя файла, в котором содержится изображение
     * @param imagePath        - ссылка на изображение
     * @param otherInformation - прочая информация об изображении в формате JSONArray
     * @see Image#Image(int, String, String, JSONArray)
     */
    public Image(String fileName, String imagePath, JSONArray otherInformation) {
        this.fileName = fileName;
        this.imagePath = imagePath;
        this.otherInformation = otherInformation;
    }

    /**
     * Функция получения значения поля {@link Image#id}
     *
     * @return Возвращает id изображения из таблицы images БД
     */
    public int getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Image#imagePath}
     *
     * @return Возвращает ссылку на изображение
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Функция получения значения поля {@link Image#otherInformation}
     *
     * @return Возвращает прочую информацию об изображении в формате JSONArray
     */
    public JSONArray getOtherInformation() {
        return otherInformation;
    }

    /**
     * Функция получения значения поля {@link Image#fileName}
     *
     * @return Возвращает имя файла, в котором содержится изображение
     */
    public String getFileName() {
        return fileName;
    }
}
