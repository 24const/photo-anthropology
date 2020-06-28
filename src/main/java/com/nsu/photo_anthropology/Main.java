package com.nsu.photo_anthropology;

import com.nsu.photo_anthropology.converters.HtmlParsing;
import com.nsu.photo_anthropology.entities.Files;
import com.nsu.photo_anthropology.exceptions.PhotoAnthropologyRuntimeException;
import com.nsu.photo_anthropology.file_workers.FileParser;
import com.nsu.photo_anthropology.file_workers.FileReadingWorker;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONArray;
import org.jsoup.HttpStatusException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String pathR = "C:/Users/Ksenia/Desktop/photo-anthropology/src/test/Instagram_Марьино.xlsx";
        String pathW = "C:/Users/Ksenia/Desktop/photo-anthropology/src/test/Instagram_Марьино1.xlsx";
        Map<Integer, List<String>> data = FileParser.readXLSXFile(pathR);

        try {
            FileInputStream fileR = new FileInputStream(new File(pathR));
            FileOutputStream fileW = new FileOutputStream(new File(pathW));
            try (Workbook workbook = new XSSFWorkbook(fileR)) {
                Workbook workbookW = new HSSFWorkbook();
                Sheet sheet = workbook.getSheetAt(0);
                Sheet new_sheet = workbookW.createSheet("Ksu");
                String cellData;
                for (int i = 0; i < sheet.getLastRowNum(); i ++) {
                    Row row = new_sheet.createRow(i);
                    int j = 0;
                    for (Cell cell : sheet.getRow(i)) {
                        row.createCell(j);
                        switch (cell.getCellType()) {
                            case STRING:
                                cellData = cell.getRichStringCellValue().getString();
                                if(String.valueOf(cellData).contains("https://www.instagram.com/p/")){
                                    cellData = HtmlParsing.getImagePath(cellData);
                                }
                                new_sheet.getRow(i).getCell(j).setCellValue(cellData);
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    new_sheet.getRow(i).getCell(j).setCellValue(cell.getDateCellValue());
                                } else {
                                    new_sheet.getRow(i).getCell(j).setCellValue(cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                new_sheet.getRow(i).getCell(j).setCellValue(cell.getBooleanCellValue());
                                break;
                            default:
                                new_sheet.getRow(i).getCell(j).setCellValue("");
                        }
                        j++;
                    }
                    System.out.println(i);
                }
                workbookW.write(fileW);
            }

        } catch (Exception e) {
            throw new PhotoAnthropologyRuntimeException("FileParser: ошибка при считывании файла.", e);
        }


        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(data.remove(0));

        Files files = new Files("C:/Users/Ksenia/Desktop/photo-anthropology/src/test/Instagram_Марьино.xlsx", jsonArray);
        FileReadingWorker fileReadingWorker = new FileReadingWorker(files, data);
    }
    private static void evaluateSTRING(Map<Integer, List<String>> data, Cell cell, int i) {

    }

    private static void evaluateNumeric(Map<Integer, List<String>> data, Cell cell, int i) {

    }

    private static void evaluateBoolean(Map<Integer, List<String>> data, Cell cell, int i) {

    }

    private static void evaluateDefault(Map<Integer, List<String>> data, int i) {
        data.get(i).add(" ");
    }
}
