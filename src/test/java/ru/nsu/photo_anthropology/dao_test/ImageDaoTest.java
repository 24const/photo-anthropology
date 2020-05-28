package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.ImageDao;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Image;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageDaoTest {

    static Image image;
    static ImageDao imageDao;
    static int savedImageId;

    @BeforeClass
    public static void setup() throws IOException {
        JSONArray columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        image = new Image("image.jpg", "https://photo", columnInfo);
        imageDao = new ImageDao();
        imageDao.setUploadFileId(18);
    }

    @Test
    public void saveImageTest() throws SQLException {
        imageDao.save(image);
        Image savedImage;
        String sql = "SELECT images.id, file_name, image_path, other_information FROM images join files on files.id = images.file_id WHERE images.id = (SELECT MAX(id) FROM images)";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                savedImageId = resultSet.getInt("id");
                savedImage = new Image(resultSet.getInt("id"), resultSet.getString("image_path"),
                        resultSet.getString("image_path"), (JSONArray) new JSONParser().parse(resultSet.getString("other_information")));
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
        Assert.assertNotNull(savedImage);
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
        imageDao.deleteById(savedImageId);
        int resultCnt;
        String sql = "SELECT count(*) as cnt FROM images WHERE id = ?";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, savedImageId);
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                resultCnt = resultSet.getInt("cnt");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
        Assert.assertEquals(0, resultCnt);
    }
}