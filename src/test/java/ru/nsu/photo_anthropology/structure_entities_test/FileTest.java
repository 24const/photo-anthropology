package ru.nsu.photo_anthropology.structure_entities_test;

import com.nsu.photo_anthropology.structure_entities.UploadedFile;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileTest {

    static JSONArray expectedColumns;
    static UploadedFile file;

    @BeforeClass
    public static void setup() {
        expectedColumns = new JSONArray();
        expectedColumns.add("image_path");
        expectedColumns.add("image_name");
        expectedColumns.add("owner");
        expectedColumns.add("date");
        file = new UploadedFile(13, "Test.txt", expectedColumns);
    }

    @Test
    public void getFileNameTest() {
        Assert.assertEquals("Test.txt", file.getFileName());
    }

    @Test
    public void getColumnNamesTest() {
        Assert.assertEquals(expectedColumns, file.getColumnNames());
    }

    @Test
    public void getIdTest() {
        Assert.assertEquals(13, file.getId());
    }
}
