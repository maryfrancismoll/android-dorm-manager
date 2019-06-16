package com.example.admin.dormmanagingapp.Model;

public class FilterString {

    private Integer id;
    private String name;

    public FilterString() {
    }

    public FilterString(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
