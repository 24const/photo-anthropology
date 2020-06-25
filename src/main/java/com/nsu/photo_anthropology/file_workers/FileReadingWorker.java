package com.nsu.photo_anthropology.file_workers;

import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.entities.Images;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileReadingWorker {

    private Files files;
    private Map<Integer, List<String>> data;
    private List<Images> images;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param files - файл, в котором содержатся данные фотографии
     * @param data  - информыция о данных, содержащихся в файле: ключ - номер строки,
     *              значение - список значенй, содержащихся в ячейках данной строки
     */
    public FileReadingWorker(Files files, Map<Integer, List<String>> data) {
        this.files = files;
        this.data = data;
        this.images = this.setImages();
    }

    /**
     * Функция получения информации об изображениях в файле
     *
     * @return Возвращает список изображений {@link Images}
     */
    private List<Images> setImages() {
        List<Images> newImages = new ArrayList<>();
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
            newImages.add(new Images(files, imagePath, columnInfo));
        }
        return newImages;
    }

    /**
     * Функция получения значения поля {@link FileReadingWorker#images}
     *
     * @return Возвращает список изображений {@link Images}
     */
    public List<Images> getImages() {
        return this.images;
    }
}
