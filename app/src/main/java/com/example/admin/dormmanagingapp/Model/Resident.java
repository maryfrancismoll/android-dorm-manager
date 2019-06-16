package com.example.admin.dormmanagingapp.Model;

import java.sql.Blob;

public class Resident {

    private Integer id;
    private String firstname;
    private String lastname;
    private Integer roomNumber;
    private String addressStreet;
    private String addressCity;
    private String originCountry;
    private String mobileNumber;
    private String emailAddress;
    private Blob photo;
    private String studentId;
    private String programme;
    private String moveInDate;
    private String moveOutDate;
    private String gender;
    private String birthdate;

    public Resident() {
    }

    public Resident(String firstname, String lastname, Integer roomNumber, String addressStreet,
                    String addressCity, String originCountry, String mobileNumber,
                    String emailAddress, Blob photo, String studentId, String programme,
                    String moveInDate, String moveOutDate, String gender, String birthdate) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.roomNumber = roomNumber;
        this.addressStreet = addressStreet;
        this.addressCity = addressCity;
        this.originCountry = originCountry;
        this.mobileNumber = mobileNumber;
        this.emailAddress = emailAddress;
        this.photo = photo;
        this.studentId = studentId;
        this.programme = programme;
        this.moveInDate = moveInDate;
        this.moveOutDate = moveOutDate;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public Resident(Integer id, String firstname, String lastname, Integer roomNumber,
                    String addressStreet, String addressCity, String originCountry,
                    String mobileNumber, String emailAddress, Blob photo, String studentId,
                    String programme, String moveInDate, String moveOutDate, String gender,
                    String birthdate) {
        this(firstname, lastname, roomNumber, addressStreet, addressCity, originCountry,
                mobileNumber, emailAddress, photo, studentId, programme, moveInDate, moveOutDate,
                gender, birthdate);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
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

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getProgramme() {
        return programme;
    }

    public void setProgramme(String programme) {
        this.programme = programme;
    }

    public String getMoveInDate() {
        return moveInDate;
    }

    public void setMoveInDate(String moveInDate) {
        this.moveInDate = moveInDate;
    }

    public String getMoveOutDate() {
        return moveOutDate;
    }

    public void setMoveOutDate(String moveOutDate) {
        this.moveOutDate = moveOutDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", roomNumber=" + roomNumber +
                ", addressStreet='" + addressStreet + '\'' +
                ", addressCity='" + addressCity + '\'' +
                ", originCountry='" + originCountry + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", photo=" + photo +
                ", studentId='" + studentId + '\'' +
                ", programme='" + programme + '\'' +
                ", moveInDate='" + moveInDate + '\'' +
                ", moveOutDate='" + moveOutDate + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}
