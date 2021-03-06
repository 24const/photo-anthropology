package ru.nsu.photo_anthropology.file_workers_test;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.file_workers.FileParser;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParserTest {

    public static String sourcePath = "Ksu_test_file.xlsx";

    @Test
    public void readXLSXFileTest() {

        Map<Integer, List<String>> expectedData = new HashMap<>();

        List<String> columnInfo = new ArrayList<>();
        columnInfo.add("image_path");
        columnInfo.add("image_name");
        columnInfo.add("owner");
        columnInfo.add("date");
        expectedData.put(0, columnInfo);

        for (int k = 1; k <= 6; k++) {
            List<String> info = new ArrayList<>();
            info.add("image" + k + ".jpg");
            info.add("image" + k);
            info.add("Ksu");
            info.add("Thu May 28 00:00:00 NOVT 2020");
            expectedData.put(k, info);
        }

        Map<Integer, List<String>> data = FileParser.readXLSXFile("src/test/resources/Ksu_test_file.xlsx");
        Assert.assertEquals(expectedData, data);
    }

    @Test
    public void getFileNameTest() {
        Assert.assertEquals("Ksu_test_file.xlsx", FileParser.getFileName(sourcePath));
    }

    @Test(expected = PhotoAnthropologyRuntimeException.class)
    public void UploadInvalidFileTest() {
        Map<Integer, List<String>> data = FileParser.readXLSXFile("ksu.txt");
    }
}
