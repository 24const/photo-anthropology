package com.nsu.photo_anthropology.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String group_name;
    private String group_question;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "groups",
            cascade = {CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST},
            orphanRemoval = true)
    private List<Tags> tags;

    public Groups() {
    }

    public Groups(String group_name, String group_question) {
        this.group_name = group_name;
        this.group_question = group_question;
    }

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_question() {
        return group_question;
    }

    public void setGroup_question(String groupQuestion) {
        this.group_question = groupQuestion;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", group_name='" + group_name + '\'' +
                ", group_question='" + group_question + '\'' +
                '}';
    }
}
