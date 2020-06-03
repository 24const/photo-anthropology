package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.FileDao;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;


public class FileDaoTest {

    private static FileDao fileDao;
    private static UploadedFile file;
    private static JSONArray columnInfo;

    @BeforeClass
    public static void setup() {
        columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        file = new UploadedFile("HelloWorld.txt", columnInfo);
        fileDao = new FileDao();
    }

    @Test
    public void saveFileTest() throws SQLException {
        Assert.assertNotNull(fileDao.save(file));
    }

    @Test
    public void deleteFileTest() throws SQLException {
        UploadedFile newFile = new UploadedFile("HelloWorld.txt", columnInfo);
        int deletedFileID = fileDao.save(newFile);
        fileDao.deleteFileById(deletedFileID);
    }
}
