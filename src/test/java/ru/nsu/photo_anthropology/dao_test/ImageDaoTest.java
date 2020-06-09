package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.FileDao;
import com.nsu.photo_anthropology.dao.ImageDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.UploadedFile;
import org.json.simple.JSONArray;
import org.junit.*;

import java.sql.SQLException;

public class ImageDaoTest {

    private static Image image;
    private static ImageDao imageDao;
    private static JSONArray columnInfo;
    private static FileDao fileDao;
    private static int savedFileId;

    @BeforeClass
    public static void setup() throws SQLException {
        columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        image = new Image("image.jpg", "https://photo", columnInfo);
        imageDao = new ImageDao();
        UploadedFile file = new UploadedFile("HelloWorld.txt", columnInfo);
        fileDao = new FileDao();
        savedFileId = fileDao.save(file);
    }

    @Before
    public void setFileID() {
        imageDao.setUploadFileId(savedFileId);
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
    public void deleteImageTest() throws SQLException {
        Image newImage = new Image("image4.jpg", "https://photo4", columnInfo);
        int deletedImageID = imageDao.saveOnlyImage(newImage);
        imageDao.deleteImageById(deletedImageID);
    }
    @AfterClass
    public static void afterAll() throws SQLException {
        fileDao.deleteFileById(savedFileId);
    }
}