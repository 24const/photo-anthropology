package com.nsu.photo_anthropology.file_workers;

import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParser {

    private FileParser() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<Integer, List<String>> readXLSXFile(String sourceFilePath) {

        Map<Integer, List<String>> data = new HashMap<>();

        try {
            FileInputStream file = new FileInputStream(new File(sourceFilePath));
            try (Workbook workbook = new XSSFWorkbook(file)) {
                Sheet sheet = workbook.getSheetAt(0);
                int i = 0;
                for (Row row : sheet) {
                    data.put(i, new ArrayList<String>());
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                evaluateSTRING(data, cell, i);
                                break;
                            case NUMERIC:
                                evaluateNumeric(data, cell, i);
                                break;
                            case BOOLEAN:
                                evaluateBoolean(data, cell, i);
                                break;
                            default:
                                evaluateDefault(data, i);
                        }
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("FileParser: ошибка при считывании файла.");
        }
        return data;
    }

    public static String getFileName(String sourceFilePath) {
        Path fileName = Paths.get(sourceFilePath).getFileName();
        return fileName.toString();
    }

    private static void evaluateSTRING(Map<Integer, List<String>> data, Cell cell, int i) {
        data.get(i).add(cell.getRichStringCellValue().getString());
    }

    private static void evaluateNumeric(Map<Integer, List<String>> data, Cell cell, int i) {
        if (DateUtil.isCellDateFormatted(cell)) {
            data.get(i).add(cell.getDateCellValue() + "");
        } else {
            data.get(i).add(cell.getNumericCellValue() + "");
        }
    }

    private static void evaluateBoolean(Map<Integer, List<String>> data, Cell cell, int i) {
        data.get(i).add(cell.getBooleanCellValue() + "");
    }

    private static void evaluateDefault(Map<Integer, List<String>> data, int i) {
        data.get(i).add(" ");
    }
}
