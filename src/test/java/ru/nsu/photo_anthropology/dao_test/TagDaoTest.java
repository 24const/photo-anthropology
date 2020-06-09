package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.TagDao;
import com.nsu.photo_anthropology.structure_entities.Group;
import com.nsu.photo_anthropology.structure_entities.Tag;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

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
        savedGroupId = groupDao.save(group);
        tag = new Tag("Тестовй тег", savedGroupId);
        tagDao = new TagDao();
        savedTagId = tagDao.saveOnlyTag(tag);
    }

    @Test
    public void saveGroupTest() throws SQLException {
        Tag newTag = new Tag("Тестовй тег2", savedGroupId);
        Assert.assertNotEquals(-1, tagDao.saveOnlyTag(newTag));
    }

    @Test
    public void deleteAllTagsInGroupTest() throws SQLException {
        Tag newTag = new Tag("Тестовй тег под удаление", savedGroupId);
        int deletedTagID = tagDao.saveOnlyTag(newTag);
        tagDao.deleteTagById(deletedTagID);
    }

    @AfterClass
    public static void deleteAllAfterTest() throws SQLException {
        GroupDao groupDao = new GroupDao();
        groupDao.deleteGroupById(savedGroupId);
    }
}
