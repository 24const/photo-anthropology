package com.nsu.photo_anthropology.structure_entities;

public class Group {

    private int id;
    private String groupName;
    private String groupQuestion;

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
        return id;
    }

    /**
     * Функция получения значения поля {@link Group#groupName}
     *
     * @return Возвращает имя группы
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Функция получения значения поля {@link Group#groupQuestion}
     *
     * @return Возвращает вопрос относящийся к группе
     */
    public String getGroupQuestion() {
        return groupQuestion;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", groupQuestion='" + groupQuestion + '\'' +
                '}';
    }
}
