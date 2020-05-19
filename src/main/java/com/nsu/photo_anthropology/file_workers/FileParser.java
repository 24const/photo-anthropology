package com.nsu.photo_anthropology.file_workers;

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

    private String path;
    private Map<Integer, List<String>> data = new HashMap<>();
    private String filePath;

    public FileParser(String path) {
        this.path = path;
        this.readFile();
        this.filePath = this.setPath(path);
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
    private String setPath(String path){
        Path filePath = Paths.get(path).getFileName();
        return filePath.toString();
    }
    public String getPath() {
        return filePath;
    }

    public Map<Integer, List<String>> getData() {
        return data;
    }
}
