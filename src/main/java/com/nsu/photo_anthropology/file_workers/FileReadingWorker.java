package com.nsu.photo_anthropology.file_workers;

import com.nsu.photo_anthropology.structure_entities.Image;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileReadingWorker {

    private String path;
    private JSONArray columnNames;
    private Map<Integer, List<String>> data;
    private List<Image> images;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param path - имя файла
     * @param data - информыция о данных, содержащихся в файле: ключ - номер строки,
     *             значение - список значенй, содержащихся в ячейках данной строки
     */
    public FileReadingWorker(String path, Map<Integer, List<String>> data) {
        this.path = path;
        this.data = data;
        this.columnNames = this.setColumnNames();
        this.images = this.setImages();
    }

    /**
     * Функция получения информации об изображениях в файле
     *
     * @return Возвращает список изображений {@link Image}
     */
    private List<Image> setImages() {
        List<Image> newImages = new ArrayList<>();
        int dataSize = this.data.keySet().size();
        for (int imageRow = 1; imageRow <= dataSize; imageRow++) {
            String imagePath = "";
            JSONArray columnInfo = new JSONArray();
            for (Object cell : data.get(imageRow)) {
                if (String.valueOf(cell).contains(".jpg")) {
                    imagePath = String.valueOf(cell);
                }
                columnInfo.add(cell);
            }
            newImages.add(new Image(this.path, imagePath, columnInfo));
        }
        return newImages;
    }

    /**
     * Функция получения информации о содержащихся в файле полях
     *
     * @return Возвращает список полей в виде JSONArray
     */
    private JSONArray setColumnNames() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(this.data.remove(0));
        return jsonArray;
    }

    /**
     * Функция получения значения поля {@link FileReadingWorker#path}
     *
     * @return Возвращает имя файла
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Функция получения значения поля {@link FileReadingWorker#columnNames}
     *
     * @return Возвращает список полей в виде JSONArray
     */
    public JSONArray getColumnNames() {
        return this.columnNames;
    }

    /**
     * Функция получения значения поля {@link FileReadingWorker#images}
     *
     * @return Возвращает список изображений {@link Image}
     */
    public List<Image> getImages() {
        return this.images;
    }
}
