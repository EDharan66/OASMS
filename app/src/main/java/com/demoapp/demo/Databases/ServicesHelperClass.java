package com.demoapp.demo.Databases;

public class ServicesHelperClass {

    String fullName, phoneNo, email, userName, password, date, gender, latitude, longitude, markerSnippet, markerName;

    public ServicesHelperClass() {}

    public ServicesHelperClass(String fullName, String phoneNo, String email, String userName, String password, String date, String gender, String latitude, String longitude) {
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.date = date;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;

    }

    public ServicesHelperClass(String markerSnippet, String markerName) {
        this.markerName = markerName;
        this.markerSnippet = markerSnippet;
    }

    public ServicesHelperClass(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMarkerSnippet() {
        return markerSnippet;
    }

    public void setMarkerSnippet(String markerSnippet) {
        this.markerSnippet = markerSnippet;
    }

    public String getMarkerName() {
        return markerName;
    }

    public void setMarkerName(String markerName) {
        this.markerName = markerName;
    }
}
