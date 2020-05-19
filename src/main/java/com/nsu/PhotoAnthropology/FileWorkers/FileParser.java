package com.nsu.PhotoAnthropology.FileWorkers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileParser {

    private String path;
    private Map<Integer, List<String>> data = new HashMap<>();

    public FileParser(String path) {
        this.path = path;
        this.readFile();
    }

    private void readFile(){

        try {
            FileInputStream file = new FileInputStream(new File(this.path));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int i = 0;
            for (Row row : sheet) {
                this.data.put(i, new ArrayList<String>());
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            this.data.get(i).add(cell.getRichStringCellValue().getString());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                this.data.get(i).add(cell.getDateCellValue() + "");
                            } else {
                                this.data.get(i).add(cell.getNumericCellValue() + "");
                            }
                            break;
                        case BOOLEAN:
                            this.data.get(i).add(cell.getBooleanCellValue() + "");
                            break;
                        default: this.data.get(i).add(" ");
                    }
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPath() {
        return path;
    }

    public Map<Integer, List<String>> getData() {
        return data;
    }
}
