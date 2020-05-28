package ru.nsu.photo_anthropology.file_workers_test;

import com.nsu.photo_anthropology.file_workers.FileParser;
import com.nsu.photo_anthropology.file_workers.FileReadingWorker;
import org.json.simple.JSONArray;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class FileReadingWorkerTest {

    static String sourcePath = "C:\\Users\\Ksenia\\Desktop\\photo-anthropology\\src\\test\\java\\ru\\nsu\\photo_anthropology\\file_workers\\Ksu_test_file.xlsx";
    static FileReadingWorker fileReadingWorker;
    static Map<Integer, List<String>> data = FileParser.readXLSXFile(sourcePath);
    static String fileName = FileParser.getFileName(sourcePath);

    @BeforeClass
    public static void setup() {
        fileReadingWorker = new FileReadingWorker(fileName, data);
    }

    @Test
    public void FileReadingWorkerInitTest() {
        Map<Integer, List<String>> testData = FileParser.readXLSXFile(sourcePath);
        FileReadingWorker newFileReadingWorker = new FileReadingWorker(sourcePath, testData);
        Assert.assertNotNull(newFileReadingWorker);
    }

    @Test
    public void getColumnNamesTest() {
        JSONArray expectedColumns = new JSONArray();
        expectedColumns.add("image_path");
        expectedColumns.add("image_name");
        expectedColumns.add("owner");
        expectedColumns.add("date");

        Assert.assertEquals(expectedColumns, fileReadingWorker.getColumnNames());
    }

    @Test
    public void getPathTest() {
        Assert.assertEquals("Ksu_test_file.xlsx", fileReadingWorker.getPath());
    }

    @Test
    public void getImagesTest() {
        Assert.assertNotNull(fileReadingWorker.getImages());
    }

}
