package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.FileDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
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

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void saveInvalidInColumnNamesFileTest() throws SQLException {
        UploadedFile wrongFile = new UploadedFile("failed_file.txt", null);
        fileDao.save(wrongFile);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void saveInvalidInNameFileTest() throws SQLException {
        UploadedFile wrongFile = new UploadedFile(null, columnInfo);
        fileDao.save(wrongFile);
    }
}
