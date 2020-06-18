package com.nsu.photo_anthropology.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.json.simple.JSONArray;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String file_name;

    @Column(columnDefinition = "json")
    @Convert(converter = JpaConverterJson.class)
    private JSONArray column_names;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "files",
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH},
            orphanRemoval = true)
    private List<Images> images;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyy-MM-dd HH:mm")
    private LocalDateTime date_created;

    public Files() {
    }

    public Files(String file_name, JSONArray column_names) {
        this.file_name = file_name;
        this.column_names = column_names;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public JSONArray getColumn_names() {
        return column_names;
    }

    public void setColumn_names(JSONArray column_names) {
        this.column_names = column_names;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Files{" +
                "id=" + id +
                ", file_name='" + file_name + '\'' +
                ", column_names=" + column_names +
                ", date_created=" + date_created +
                '}';
    }
}
