package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.ImageDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class ImageDaoTest {

    private static Image image;
    private static ImageDao imageDao;
    private static JSONArray columnInfo;

    @BeforeClass
    public static void setup() {
        columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        image = new Image("image.jpg", "https://photo", columnInfo);
        imageDao = new ImageDao();
    }

    @Before
    public void setFileID() {
        imageDao.setUploadFileId(1);
    }

    @Test
    public void saveImageTest() throws SQLException {
        Image newImage = new Image("image4.jpg", "https://photo4", columnInfo);
        int savedImageId = imageDao.saveOnlyImage(newImage);
        System.out.println(savedImageId);
        imageDao.deleteImageById(savedImageId);
        Assert.assertNotEquals(-1, savedImageId);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void saveInvalidImageTest() throws SQLException {
        Image newImage = new Image(null, null, columnInfo);
        imageDao.saveOnlyImage(newImage);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void saveImageWithNonExistingFileTest() throws SQLException {
        imageDao.setUploadFileId(713);
        imageDao.saveOnlyImage(image);
    }

    @Test
    public void setAndGetUploadFileIdTest() {
        Assert.assertEquals(1, imageDao.getUploadFileId());
    }

    @Test
    public void deleteImageTest() throws SQLException {
        Image newImage = new Image("image4.jpg", "https://photo4", columnInfo);
        int deletedImageID = imageDao.saveOnlyImage(newImage);
        imageDao.deleteImageById(deletedImageID);
    }
}