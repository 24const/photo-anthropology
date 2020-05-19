package com.nsu.photo_anthropology.file_workers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nsu.photo_anthropology.structure_classes.Image;
import org.json.simple.JSONArray;

public class FileReadingWorker {

    private static String[] FILE_FORMATS = {".jpeg", ".jpg", ".png"};
    private String path;
    private JSONArray columnNames = new JSONArray();
    private Map<Integer, List<String>> data;
    private List<Image> images = new ArrayList<>();

    public FileReadingWorker(String path, Map<Integer, List<String>> data) {
        this.path = path;
        this.data = data;
        this.setColumnNames();
        this.setImages();
    }

    private void setImages(){
        int dataSize = this.data.keySet().size();
        for(int imageRow = 1; imageRow <= dataSize; imageRow++){
            String imagePath = "";
            JSONArray columnInfo = new JSONArray();
            for(Object cell:data.get(imageRow)){
                if(String.valueOf(cell).contains(".jpg")){
                    imagePath = String.valueOf(cell);

                }
                columnInfo.add(cell);
            }
            this.images.add(new Image(this.path, imagePath, columnInfo));
        }

    }

    private void setColumnNames(){
        for(String element:this.data.remove(0))
        this.columnNames.add(element);
    }

    public String getPath() {
        return path;
    }

    public JSONArray getColumnNames() {
        return columnNames;
    }

    public List<Image> getImages() {
        return images;
    }
}