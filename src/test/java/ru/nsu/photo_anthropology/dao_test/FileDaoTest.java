package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.FileDao;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.File;
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


public class FileDaoTest {

    static FileDao fileDao;
    static File file;
    static int savedFileId;

    @BeforeClass
    public static void setup() throws IOException {
        JSONArray columnInfo = new JSONArray();
        columnInfo.add("columnOne");
        columnInfo.add("columnTwo");
        columnInfo.add("columnThree");
        file = new File("HelloWorld.txt", columnInfo);
        fileDao = new FileDao();
    }

    @Test
    public void saveFileTest() throws SQLException {

        fileDao.save(file);
        File savedFile = null;

        //TODO: тестировать СУБД не требуется, нужно тестировать DAO, т.е. если метод save сохраняет данные, то метод getById их получает.
        String sql = "SELECT * FROM files where id = (SELECT MAX(id) FROM files)";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                savedFileId = resultSet.getInt("id");
                savedFile = new File(resultSet.getInt("id"), resultSet.getString("file_name"), (JSONArray) new JSONParser().parse(resultSet.getString("column_names")));
            }
        } catch (SQLException | ParseException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД в GroupDao.getById(int id).");
        }
        Assert.assertEquals(file.toString(), savedFile.toString());
    }

    @Test
    public void getIdOfSavedFileTest() {
        Assert.assertNotNull(fileDao.getIdOfSavedFile());
    }

    @Test
    public void getDeleteSqlRequestTest() {
        //TODO: зачем тестировать строковую константу?
        String deleteRequest = fileDao.getDeleteSqlRequest();
        Assert.assertEquals("DELETE FROM files WHERE id = ?", deleteRequest);
    }

    @Test
    public void deleteFileTest() throws SQLException {
        //TODO: аналогично пункту выше, БД мы не тестируем, а тестируем только DAO
        fileDao.deleteById(savedFileId);
        int resultCnt;
        String sql = "SELECT count(*) as cnt FROM files WHERE id = ?";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, savedFileId);
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
