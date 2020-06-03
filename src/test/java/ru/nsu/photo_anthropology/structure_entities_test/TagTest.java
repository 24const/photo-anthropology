package ru.nsu.photo_anthropology.structure_entities_test;

import com.nsu.photo_anthropology.structure_entities.Tag;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TagTest {
    static Tag tag;

    @BeforeClass
    public static void setup() {
        tag = new Tag(13, "test_tag", "test_group");
    }

    @Test
    public void tagWithoutIdTest() {
        Tag newTag = new Tag("test_tag", "test_group");
        Assert.assertNotNull(newTag);
    }

    @Test
    public void getTagNameTest() {
        Assert.assertEquals("test_tag", tag.getTagName());
    }

    @Test
    public void getGroupNameTest() {
        Assert.assertEquals("test_group", tag.getGroupName());
    }

    @Test
    public void getIdTest() {
        Assert.assertEquals(13, tag.getId());
    }
}
