package com.nsu.photo_anthropology.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nsu.photo_anthropology.converters.JpaConverterJson;
import org.json.simple.JSONArray;

import javax.persistence.*;

@Entity
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private Files files;

    private String image_path;

    @Column(columnDefinition = "json")
    @Convert(converter = JpaConverterJson.class)
    private JSONArray other_information;

    public Images() {
    }

    public Images(Files files, String image_path, JSONArray other_information) {
        this.files = files;
        this.image_path = image_path;
        this.other_information = other_information;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Files getFiles() {
        return files;
    }

    public void setFiles(Files files) {
        this.files = files;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public JSONArray getOther_information() {
        return other_information;
    }

    public void setOther_information(JSONArray other_information) {
        this.other_information = other_information;
    }

    @Override
    public String toString() {
        return "Images{" +
                "id=" + id +
                ", image_path='" + image_path + '\'' +
                ", other_information=" + other_information +
                '}';
    }
}
