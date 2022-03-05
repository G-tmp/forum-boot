package com.gtmp.POJO;

public class Permission {
    private Integer id;
    private String operation;

    public Permission(){}


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                '}';
    }
}
