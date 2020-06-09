package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.FileDao;
import com.nsu.photo_anthropology.dao.InitDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;
import org.json.simple.JSONArray;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FileDaoTest {

    private static FileDao fileDao;
    private static UploadedFile file;
    private static JSONArray columnInfo;
    private static int savedFileId;

    @BeforeClass
    public static void setup() throws SQLException {
        InitDao.createDbSchema();
        columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        file = new UploadedFile("HelloWorld.txt", columnInfo);
        fileDao = new FileDao();
        savedFileId = fileDao.save(file);
    }

    @AfterClass
    public static void deleteSavedGroups() throws SQLException {
        fileDao.deleteFileById(savedFileId);
    }

    @Test
    public void saveFileTest() throws SQLException {
        UploadedFile file = new UploadedFile("HelloWorld2.txt", columnInfo, null);
        int idOfSavedFile = fileDao.save(file);
        fileDao.deleteFileById(idOfSavedFile);
        Assert.assertNotEquals(-1, idOfSavedFile);
    }

    @Test
    public void saveFileWithImagesTest() throws SQLException {
        Image newImage = new Image("Ksu_test_file.xlsx", "https://photo4", columnInfo);
        List<Image> imagesInFile = new ArrayList<>();
        imagesInFile.add(newImage);
        UploadedFile file = new UploadedFile("src/test/resources/Ksu_test_file.xlsx", columnInfo, imagesInFile);
        int idOfSavedFile = fileDao.save(file);
        fileDao.deleteFileById(idOfSavedFile);
        Assert.assertNotEquals(-1, idOfSavedFile);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void deleteFileTest() throws SQLException {
        UploadedFile newFile = new UploadedFile("HelloWorld.txt", columnInfo);
        int deletedFileID = fileDao.save(newFile);
        fileDao.deleteFileById(deletedFileID);
        fileDao.getFileById(deletedFileID);
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

    @Test
    public void getFileById() {
        UploadedFile file = fileDao.getFileById(savedFileId);
        UploadedFile file2 = new UploadedFile(savedFileId, "HelloWorld.txt", null);
        Assert.assertEquals(file, file2);
    }
}
