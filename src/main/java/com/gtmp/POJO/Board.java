package com.gtmp.POJO;

public class Board {
    private Integer id;
    private String name;
    private String ename;
    private String description;

    public Board() {

    }

    public Integer getId() {
        return id;
    }

    public Board setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Board setName(String name) {
        this.name = name;
        return this;

    }

    public String getEname() {
        return ename;
    }

    public Board setEname(String ename) {
        this.ename = ename;
        return this;

    }

    public String getDescription() {
        return description;
    }

    public Board setDescription(String description) {
        this.description = description;
        return this;

    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ename='" + ename + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
