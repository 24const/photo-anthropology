package com.nsu.photo_anthropology.structure_entities;

public class Tag {
    private int id;
    private String tagName;
    private String groupName;

    /**
     * Конструктор - создание нового объекта
     *
     * @param id        - id файла в таблице tags БД
     * @param tagName   - имя тега
     * @param groupName - имя группы, к которой принадлежит тег
     * @see Tag#Tag(String, String)
     */
    public Tag(int id, String tagName, String groupName) {
        this.id = id;
        this.tagName = tagName;
        this.groupName = groupName;
    }

    /**
     * Конструктор - создание нового объекта
     *
     * @param tagName   - имя тега
     * @param groupName - имя группы, к которой принадлежит тег
     * @see Tag#Tag(int, String, String)
     */
    public Tag(String tagName, String groupName) {
        this.tagName = tagName;
        this.groupName = groupName;
    }

    /**
     * Функция получения значения поля {@link Tag#id}
     *
     * @return Возвращает id тега из таблицы tags БД
     */
    public int getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Tag#tagName}
     *
     * @return Возвращает имя тега
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Функция получения значения поля {@link Tag#groupName}
     *
     * @return Возвращает имя группы, к которой принадлежит тег
     */
    public String getGroupName() {
        return groupName;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag_name='" + tagName + '\'' +
                ", groupName=" + groupName +
                '}';
    }
}
