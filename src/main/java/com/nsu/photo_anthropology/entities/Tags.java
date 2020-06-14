package com.nsu.photo_anthropology.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tag_name;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Groups groups;

    public Tags() {
    }

    public Tags(String tag_name, Groups groups) {
        this.tag_name = tag_name;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    @JsonIgnore
    public Groups getGroups() {
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "id=" + id +
                ", tag_name='" + tag_name + '\'' +
                '}';
    }
}
