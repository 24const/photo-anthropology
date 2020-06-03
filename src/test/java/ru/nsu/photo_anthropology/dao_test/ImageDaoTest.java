package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.ImageDao;
import com.nsu.photo_anthropology.structure_entities.Image;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class ImageDaoTest {

    private static Image image;
    private static ImageDao imageDao;
    private static int savedImageId;
    private static JSONArray columnInfo;

    @BeforeClass
    public static void setup() {
        columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        image = new Image("image.jpg", "https://photo", columnInfo);
        imageDao = new ImageDao();
        imageDao.setUploadFileId(13);
        savedImageId = imageDao.save(image);
    }

    @Test
    public void saveImageTest() {
        Image newImage = new Image("image4.jpg", "https://photo4", columnInfo);
        Assert.assertNotEquals(0, imageDao.save(newImage));
    }

    @Test
    public void getDeleteSqlRequestTest() {
        String deleteRequest = imageDao.getDeleteSqlRequest();
        Assert.assertEquals("DELETE FROM images WHERE id = ?", deleteRequest);
    }

    @Test
    public void setAndGetUploadFileIdTest() {
        imageDao.setUploadFileId(13);
        Assert.assertEquals(13, imageDao.getUploadFileId());
    }

    @Test
    public void deleteImageTest() throws SQLException {
        Image newImage = new Image("image4.jpg", "https://photo4", columnInfo);
        int deletedImageID = imageDao.save(newImage);
        imageDao.deleteImageById(deletedImageID);
    }
}