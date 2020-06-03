package ru.nsu.photo_anthropology.structure_entities_test;

import com.nsu.photo_anthropology.structure_entities.Group;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class GroupTest {

    static Group group;

    @BeforeClass
    public static void setup() {
        group = new Group(13, "test_group", "Successful?");
    }

    @Test
    public void getGroupNameTest() {
        Assert.assertEquals("test_group", group.getGroupName());
    }

    @Test
    public void getGroupQuestionTest() {
        Assert.assertEquals("Successful?", group.getGroupQuestion());
    }

    @Test
    public void getIdTest() {
        Assert.assertEquals(13, group.getId());
    }
}
