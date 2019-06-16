package com.example.admin.dormmanagingapp.Model;

public class Event {
    private Integer id;
    private Integer modelId;
    private String modelTableName;
    private EventType eventType;
    private String title;
    private String date;
    private String time;
    private String details;

    public Event() {
    }

    public Event(Integer id, Integer modelId, String modelTableName, EventType eventType, String title, String date, String time, String details) {
        this.id = id;
        this.modelId = modelId;
        this.modelTableName = modelTableName;
        this.eventType = eventType;
        this.title = title;
        this.date = date;
        this.time = time;
        this.details = details;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelTableName() {
        return modelTableName;
    }

    public void setModelTableName(String modelTableName) {
        this.modelTableName = modelTableName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
