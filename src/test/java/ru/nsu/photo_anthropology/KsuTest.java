package ru.nsu.photo_anthropology;

import com.nsu.photo_anthropology.file_workers.FileSavingToDBWorker;
import org.junit.Test;

public class KsuTest {
    @Test
    public void myTest() {
//        String filePath = "C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\java\\com\\nsu\\photo_anthropology\\vk_photo_people_HM.xlsx";
        String filePath ="C:\\Users\\Эльдорадо\\Desktop\\photo-anthropology\\src\\main\\java\\com\\nsu\\photo_anthropology\\Instagram_Марьино.xlsx";
        FileSavingToDBWorker.saveFileInfo(filePath);

    }
}
