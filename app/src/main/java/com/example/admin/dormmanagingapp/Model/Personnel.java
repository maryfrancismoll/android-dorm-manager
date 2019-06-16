package com.example.admin.dormmanagingapp.Model;

public class Personnel {

    private Integer id;
    private String employeeId;
    private String firstname;
    private String lastname;
    private String gender;
    private String type;
    private String mobileNumber;
    private String emailAddress;

    public Personnel() {
    }

    public Personnel(String employeeId, String firstname, String lastname, String gender, String type, String mobileNumber, String emailAddress) {
        this.employeeId = employeeId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.type = type;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
    }

    public Personnel(Integer id, String employeeId, String firstname, String lastname, String gender, String type, String mobileNumber, String emailAddress) {
        this(employeeId, firstname, lastname, gender, type, mobileNumber, emailAddress);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
