package com.nsu.photo_anthropology.structure_entities;

import java.util.Objects;

public class Group {

    private int id = -1;
    private String groupName;
    private String groupQuestion;
    private String tagsInGroup;

    /**
     * Конструктор - создание нового объекта
     *
     * @param id            - id группы в таблиице groups БД
     * @param groupName     - название группы
     * @param groupQuestion - вопрос к группе
     * @see Group#Group(String, String)
     */
    public Group(int id, String groupName, String groupQuestion) {
        this.id = id;
        this.groupName = groupName;
        this.groupQuestion = groupQuestion;
    }

    /**
     * Конструктор - создание нового объекта
     *
     * @param id            - id группы в таблиице groups БД
     * @param groupName     - название группы
     * @param groupQuestion - вопрос к группе
     * @param tagsInGroup   - теги, сохраняемые в группе
     * @see Group#Group(String, String)
     */
    public Group(int id, String groupName, String groupQuestion, String tagsInGroup) {
        this.id = id;
        this.groupName = groupName;
        this.groupQuestion = groupQuestion;
        this.tagsInGroup = tagsInGroup;
    }

    /**
     * Конструктор - создание нового объекта
     *
     * @param groupName     - название группы
     * @param groupQuestion - вопрос к группе
     * @param tagsInGroup   - теги, сохраняемые в группе
     * @see Group#Group(int, String, String)
     */
    public Group(String groupName, String groupQuestion, String tagsInGroup) {
        this.groupName = groupName;
        this.groupQuestion = groupQuestion;
        this.tagsInGroup = tagsInGroup;
    }

    /**
     * Конструктор - создание нового объекта
     *
     * @param groupName     - название группы
     * @param groupQuestion - вопрос к группе
     * @see Group#Group(int, String, String)
     */
    public Group(String groupName, String groupQuestion) {
        this.groupName = groupName;
        this.groupQuestion = groupQuestion;
    }

    /**
     * Функция получения значения поля {@link Group#id}
     *
     * @return Возвращает id группы из таблицы groups БД
     */
    public int getId() {
        return this.id;
    }

    /**
     * Функция получения значения поля {@link Group#groupName}
     *
     * @return Возвращает имя группы
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * Функция получения значения поля {@link Group#groupQuestion}
     *
     * @return Возвращает вопрос относящийся к группе
     */
    public String getGroupQuestion() {
        return this.groupQuestion;
    }

    /**
     * Функция получения значения поля {@link Group#tagsInGroup}
     *
     * @return Возвращает существующие в группе теги
     */
    public String getTagsInGroup() {
        return this.tagsInGroup;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Group group = (Group) object;
        return id == group.id &&
                Objects.equals(groupName, group.groupName) &&
                Objects.equals(groupQuestion, group.groupQuestion) &&
                Objects.equals(tagsInGroup, group.tagsInGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, groupName, groupQuestion, tagsInGroup);
    }
}
