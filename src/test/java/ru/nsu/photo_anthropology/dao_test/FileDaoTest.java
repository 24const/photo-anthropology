package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.FileDao;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;


public class FileDaoTest {

    static FileDao fileDao;
    static UploadedFile file;

    @BeforeClass
    public static void setup() throws IOException {
        JSONArray columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        file = new UploadedFile("HelloWorld.txt", columnInfo);
        fileDao = new FileDao();
    }

    @Test
    public void saveFileTest() throws SQLException {
        fileDao.save(file);
        Assert.assertNotNull(fileDao.getIdOfSavedFile());
    }

    @Test
    public void getIdOfSavedFileTest() {
        Assert.assertNotNull(fileDao.getIdOfSavedFile());
    }

    @Test
    public void deleteFileTest() throws SQLException {
        fileDao.deleteById(fileDao.getIdOfSavedFile());
    }
}
