package ru.nsu.photo_anthropology;

import com.nsu.photo_anthropology.dao.*;
import com.nsu.photo_anthropology.file_workers.FileParser;
import com.nsu.photo_anthropology.file_workers.FileReadingWorker;
import com.nsu.photo_anthropology.structure_classes.File;
import com.nsu.photo_anthropology.structure_classes.Image;
import org.junit.Test;

public class KsuTest {
    @Test
    public void myTest() {
        String filePath = "C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\java\\com\\nsu\\photo_anthropology\\vk_photo_people_HM.xlsx";
        FileParser fileParser = new FileParser(filePath);
        FileReadingWorker fileReadingWorker = new FileReadingWorker(fileParser.getPath(), fileParser.getData());
        fileReadingWorker.getColumnNames();
        fileReadingWorker.getImages();
        Dao fileDao = new FileDao();
        Dao groupDao = new GroupDao();
        Dao tagDao = new TagDao();
        fileDao.save(new File(fileReadingWorker.getPath(), fileReadingWorker.getColumnNames()));
        Dao imageDao = new ImageDao();
        for(Image image: fileReadingWorker.getImages()){
            imageDao.save(image);
        }
    }
}
