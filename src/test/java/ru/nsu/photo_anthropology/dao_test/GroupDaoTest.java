package ru.nsu.photo_anthropology.dao_test;

import com.nsu.photo_anthropology.dao.GroupDao;
import com.nsu.photo_anthropology.dao.InitDao;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.structure_entities.Group;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class GroupDaoTest {

    static Group group;
    static int savedGroupId;
    private static GroupDao groupDao;

    @BeforeClass
    public static void setup() throws SQLException {
        InitDao.createDbSchema();
        group = new Group("Тестовая группа", "Успешное прохождение теста??");
        groupDao = new GroupDao();
        savedGroupId = groupDao.save(group);
    }

    @AfterClass
    public static void deleteSavedGroups() throws SQLException {
        groupDao.deleteGroupById(savedGroupId);
    }

    @Test
    public void saveGroupTest() throws SQLException {
        int savedGroupId = groupDao.save(group);
        groupDao.deleteGroupById(savedGroupId);
        Assert.assertNotEquals(-1, savedGroupId);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void saveInvalidGroupTest() throws SQLException {
        Group invalidGroup = new Group(null, null);
        groupDao.save(invalidGroup);
    }

    @Test
    public void getByIdTest() {
        Group group = new Group(savedGroupId, "Тестовая группа", "Успешное прохождение теста??");
        Assert.assertEquals(group, groupDao.getById(savedGroupId));
    }

    @Test
    public void getNonExistsGroupByIdTest() {
        Assert.assertNull(groupDao.getById(713));
    }

    @Test
    public void updateGroupTest() throws SQLException {
        Group changedGroup = new Group(savedGroupId, "Тестовая группа2", "Успешное ли?");
        groupDao.update(changedGroup);
        Assert.assertEquals(changedGroup, groupDao.getById(savedGroupId));
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void updateInvalidGroupWithoutTagsTest() throws SQLException {
        Group changedGroup = new Group(savedGroupId, null, null);
        groupDao.update(changedGroup);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void updateInvalidGroupWithTagsTest() throws SQLException {
        Group changedGroup = new Group(savedGroupId, null, null, "invalid, test");
        groupDao.update(changedGroup);
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void updateInvalidGroupWithInvalidTagsTest() throws SQLException {
        Group changedGroup = new Group(savedGroupId, null, null, " , , ");
        groupDao.update(changedGroup);
    }

    @Test
    public void deleteGroupTest() throws SQLException {
        Group deletedGroup = new Group("Группа под удаление", "Успешное ли?");
        int idOfDeletedGroup = groupDao.save(deletedGroup);
        groupDao.deleteGroupById(idOfDeletedGroup);
        Assert.assertNull(groupDao.getById(idOfDeletedGroup));
    }
}
