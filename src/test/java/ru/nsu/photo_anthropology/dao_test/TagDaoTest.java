package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.TagDao;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDaoTest {
    private static TagDao tagDao;
    static Tag tag;
    static int savedTagId;
    static Group group;
    static int savedGroupId;

    @BeforeClass
    public static void setup() {
        group = new Group("Тестовая группа для тега", "Успешное прохождение теста??");
        GroupDao groupDao = new GroupDao();
        groupDao.save(group);
        tag = new Tag("Тестовй тег", "Тестовая группа для тега");
        tagDao = new TagDao();
    }

    @Test
    public void saveGroupTest() throws SQLException {
        tagDao.save(tag);
        String sql = "SELECT id, group_id FROM tags WHERE id = (SELECT max(id) FROM tags)";

        DbConnector dbConnector = DbConnector.getInstance();
        Connection connection = dbConnector.getConnection();

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = stm.executeQuery()) {
                resultSet.next();
                savedTagId = resultSet.getInt("id");
                savedGroupId = resultSet.getInt("group_id");
            }
        } catch (SQLException e) {
            throw new PhotoAnthropologyRuntimeException("Невозможно получить информацию из БД.");
        }
        Assert.assertNotNull(savedTagId);
    }

    @Test
    public void getDeleteSqlRequestTest(){
        String deleteRequest = tagDao.getDeleteSqlRequest();
        Assert.assertEquals("DELETE FROM tags WHERE id = ?", deleteRequest);
    }

    @Test
    public void getAllTagsInGroupTest(){
        List<Tag> expectedListOfTags = new ArrayList<>();
        expectedListOfTags.add(tag);
        Group group = new Group(savedGroupId, "Тестовая группа для тега", "Успешное прохождение теста??");
        Assert.assertEquals(expectedListOfTags, tagDao.getAllTagsInGroup(group));
    }

    @Test
    public void deleteAllTagsInGroupTest(){
        tagDao.deleteAllTagsInGroup(savedGroupId);
        int resultCnt;
        String sql = "SELECT count(*) as cnt FROM tags WHERE group_id = ?";

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

}
