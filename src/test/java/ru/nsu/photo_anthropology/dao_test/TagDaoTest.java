package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.TagDao;
import com.nsu.photo_anthropology.db_tools.DbConnector;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Image;
import com.nsu.photo_anthropology.structure_entities.Tag;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagDaoTest {
    static Tag tag;
    static int savedTagId;
    static Group group;
    static int savedGroupId;
    private static TagDao tagDao;

    @BeforeClass
    public static void setup() throws SQLException {
        group = new Group("Тестовая группа для тега", "Успешное прохождение теста??");
        GroupDao groupDao = new GroupDao();
        groupDao.save(group);
        tag = new Tag("Тестовй тег", "Тестовая группа для тега");
        tagDao = new TagDao();
        savedTagId = tagDao.save(tag);
    }

    @Test
    public void saveGroupTest() throws SQLException {
        Tag newTag = new Tag("Тестовй тег2", "Тестовая группа для тега");
        Assert.assertNotEquals(0, tagDao.save(newTag));
    }

    @Test
    public void getDeleteSqlRequestTest() {
        String deleteRequest = tagDao.getDeleteSqlRequest();
        Assert.assertEquals("DELETE FROM tags WHERE id = ?", deleteRequest);
    }

    @Test
    public void getAllTagsInGroupTest() {
        List<Tag> expectedListOfTags = new ArrayList<>();
        expectedListOfTags.add(tag);
        Group group = new Group(savedGroupId, "Тестовая группа для тега", "Успешное прохождение теста??");
        Assert.assertEquals(expectedListOfTags, tagDao.getAllTagsInGroupByGroupId(group.getId()));
    }

    @Test
    public void deleteAllTagsInGroupTest() throws SQLException {
        Tag newTag = new Tag("Тестовй тег под удаление", "Тестовая группа для тега");
        int deletedTagID = tagDao.save(newTag);
        tagDao.deleteTagById(deletedTagID);
    }

}
