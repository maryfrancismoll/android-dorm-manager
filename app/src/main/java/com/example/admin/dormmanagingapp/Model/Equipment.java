package com.example.admin.dormmanagingapp.Model;

public class Equipment {

    private Integer id;
    private String code;
    private String name;
    private String serialNumber;
    private Integer yearPurchased;
    private String brand;

    public Equipment() {
    }

    public Equipment(String code, String name, String serialNumber, Integer yearPurchased, String brand) {
        this.code = code;
        this.name = name;
        this.serialNumber = serialNumber;
        this.yearPurchased = yearPurchased;
        this.brand = brand;
    }

    public Equipment(Integer id, String code, String name, String serialNumber, Integer yearPurchased, String brand) {
        this(code, name, serialNumber, yearPurchased, brand);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getYearPurchased() {
        return yearPurchased;
    }

    public void setYearPurchased(Integer yearPurchased) {
        this.yearPurchased = yearPurchased;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
