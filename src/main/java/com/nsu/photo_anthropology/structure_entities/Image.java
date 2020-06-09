package com.nsu.photo_anthropology.structure_entities;

import org.json.simple.JSONArray;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Image image = (Image) object;
        return id == image.id &&
                Objects.equals(imagePath, image.imagePath) &&
                Objects.equals(fileName, image.fileName) &&
                Objects.equals(otherInformation, image.otherInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imagePath, fileName, otherInformation);
    }
}
