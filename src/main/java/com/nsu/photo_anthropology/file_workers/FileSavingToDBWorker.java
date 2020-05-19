package com.nsu.photo_anthropology.file_workers;

import com.nsu.photo_anthropology.dao.*;
import com.nsu.photo_anthropology.structure_classes.File;
import com.nsu.photo_anthropology.structure_classes.Image;

public class FileSavingToDBWorker {

    public static void saveFileInfo(String sourcePath){
        FileParser fileParser = new FileParser(sourcePath);
        FileReadingWorker fileReadingWorker = new FileReadingWorker(fileParser.getPath(), fileParser.getData());

        Dao fileDao = new FileDao();
        fileDao.save(new File(fileReadingWorker.getPath(), fileReadingWorker.getColumnNames()));

        Dao imageDao = new ImageDao();
        for(Image image: fileReadingWorker.getImages()){
            imageDao.save(image);
        }
    }
}
