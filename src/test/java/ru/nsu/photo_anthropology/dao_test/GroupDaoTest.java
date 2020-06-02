package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupDaoTest {

    static Group group;
    static int savedGroupId;
    private static GroupDao groupDao;

    @BeforeClass
    public static void setup() {
        group = new Group("Тестовая группа", "Успешное прохождение теста??");
        groupDao = new GroupDao();
    }

    @Test
    public void saveGroupTest() throws SQLException {
        groupDao.save(group);
        String sql = "SELECT MAX(id) as max_id FROM groups";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                savedGroupId = resultSet.getInt("max_id");
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
        Assert.assertNotNull(savedGroupId);
    }

    @Test
    public void getByIdTest() {
        Group group = new Group(savedGroupId, "Тестовая группа", "Успешное прохождение теста??");
        Assert.assertEquals(group.toString(), groupDao.getById(savedGroupId));
    }

    @Test
    public void getDeleteSqlRequestTest() {
        String deleteRequest = groupDao.getDeleteSqlRequest();
        Assert.assertEquals("DELETE FROM groups WHERE id = ?", deleteRequest);
    }

    @Test
    public void updateGroupTest() {
        Group changedGroup = new Group(savedGroupId, "Тестовая группа2", "Успешное ли?");
        Assert.assertEquals(changedGroup, groupDao.getById(group.getId()));
    }

    @Test
    public void deleteGroupTest() throws SQLException {
        groupDao.deleteById(savedGroupId);
        int resultCnt;
        String sql = "SELECT count(*) as cnt FROM groups WHERE id = ?";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, savedGroupId);
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
//TODO: не хватает теста для транзакции. Т.е. часть тегов должна удалиться успешно, а один с ошибкой и в БД ничего не должно сохраниться
}
