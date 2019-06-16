package com.example.admin.dormmanagingapp.Model;

public class EventType {

    private Integer id;
    private String name;
    private String modelTblAllowed;

    public EventType() {
    }

    public EventType(Integer id, String name, String modelTblAllowed) {
        this(name, modelTblAllowed);
        this.id = id;
    }

    public EventType(String name, String modelTblAllowed) {
        this.name = name;
        this.modelTblAllowed = modelTblAllowed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelTblAllowed() {
        return modelTblAllowed;
    }

    public void setModelTblAllowed(String modelTblAllowed) {
        this.modelTblAllowed = modelTblAllowed;
    }
}
