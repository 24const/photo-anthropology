package com.nsu.photo_anthropology.file_workers;

import com.nsu.photo_anthropology.dao.FileDao;
import com.nsu.photo_anthropology.dao.ImageDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.File;
import com.nsu.photo_anthropology.structure_entities.Image;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class FileSavingToDBWorker {

    private FileSavingToDBWorker() {
        throw new IllegalStateException("Utility class");
    }

    public static void saveFileInfo(String sourcePath) {

        Map<Integer, List<String>> data = FileParser.readXLSXFile(sourcePath);
        String fileName = FileParser.getFileName(sourcePath);
        FileReadingWorker fileReadingWorker = new FileReadingWorker(fileName, data);
        FileDao fileDao = new FileDao();
        try {
            fileDao.save(new File(fileReadingWorker.getPath(), fileReadingWorker.getColumnNames()));
            ImageDao imageDao = new ImageDao();
            imageDao.setUploadFileId(fileDao.getIdOfSavedFile());
            for (Image image : fileReadingWorker.getImages()) {
                imageDao.save(image);
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("FileSavingToDBWorker: не удалось сохранитьфайл в БД.");
        }
    }
}
