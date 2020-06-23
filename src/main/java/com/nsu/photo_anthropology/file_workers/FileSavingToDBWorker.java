//package com.nsu.photo_anthropology.file_workers;
//
//import com.nsu.photo_anthropology.entities.Files;
//import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
//
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Map;
//
//public class FileSavingToDBWorker {
//
//    private FileSavingToDBWorker() {
//        throw new IllegalStateException("Utility class");
//    }
//
//    /**
//     * Функция запускающая сохранение данных исходного файла в БД
//     *
//     * @param sourcePath - абсолютный путь к загруженному файлу
//     */
//    public static void saveFileInfo(String sourcePath) {
//
//        Map<Integer, List<String>> data = FileParser.readXLSXFile(sourcePath);
//        String fileName = FileParser.getFileName(sourcePath);
//        Files files = new
//
//        FileReadingWorker fileReadingWorker = new FileReadingWorker(fileName, data);
//
//    }
//}
