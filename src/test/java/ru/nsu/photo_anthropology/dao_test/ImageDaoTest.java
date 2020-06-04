package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.ImageDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import org.json.simple.JSONArray;
import org.junit.*;

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
        imageDao.setUploadFileId(13);
    }

    @Before
    public void setFileID() {
        imageDao.setUploadFileId(13);
    }

    @Test
    public void saveImageTest() throws SQLException {
        Image newImage = new Image("image4.jpg", "https://photo4", columnInfo);
        int savedImageId = imageDao.saveOnlyImage(newImage);
        Assert.assertNotEquals(0, savedImageId);
        imageDao.deleteImageById(savedImageId);

    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void saveInvalidImageTest() {
        Image newImage = new Image(null, null, columnInfo);
        imageDao.save(newImage);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void saveImageWithNonExistingFileTest() {
        imageDao.setUploadFileId(713);
        imageDao.save(image);
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