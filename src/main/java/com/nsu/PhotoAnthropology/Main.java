package com.nsu.PhotoAnthropology;

import com.nsu.PhotoAnthropology.DAO.Dao;
import com.nsu.PhotoAnthropology.DAO.FileDao;
import com.nsu.PhotoAnthropology.FileWorkers.FileParser;
import com.nsu.PhotoAnthropology.FileWorkers.FileReadingWorker;

public class Main {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\java\\com\\nsu\\PhotoAnthropology\\vk_photo_people_HM.xlsx";
        FileParser fileParser = new FileParser(filePath);
        FileReadingWorker fileReadingWorker = new FileReadingWorker(filePath, fileParser.getData());
        fileReadingWorker.getColumnNames();
        fileReadingWorker.getImages();

//        Dao groupDao = new FileDao();
//        groupDao.save(new com.nsu.PhotoAnthropology.StructureClasses.File(filePath, fileReadingWorker.getColumnNames()));

    }
}
