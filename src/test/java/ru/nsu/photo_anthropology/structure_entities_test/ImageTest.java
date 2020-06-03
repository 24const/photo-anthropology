package ru.nsu.photo_anthropology.structure_entities_test;

import com.nsu.photo_anthropology.structure_entities.Image;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageTest {

    static JSONArray expectedColumns;
    static Image image;

    @BeforeClass
    public static void setup() {
        expectedColumns = new JSONArray();
        expectedColumns.add("image_path");
        expectedColumns.add("image_name");
        expectedColumns.add("owner");
        expectedColumns.add("date");
        image = new Image(13, "Test.txt", "test_image.jpg", expectedColumns);
    }

    @Test
    public void getImagePathTest() {
        Assert.assertEquals("test_image.jpg", image.getImagePath());
    }

    @Test
    public void getOtherInformationTest() {
        Assert.assertEquals(expectedColumns, image.getOtherInformation());
    }

    @Test
    public void getFileNameTest() {
        Assert.assertEquals("Test.txt", image.getFileName());
    }

    @Test
    public void getIdTest() {
        Assert.assertEquals(13, image.getId());
    }
}
