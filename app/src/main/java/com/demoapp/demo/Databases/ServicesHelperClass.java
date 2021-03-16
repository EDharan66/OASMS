package com.demoapp.demo.Databases;

import com.demoapp.demo.Common.SendNotificationPack.Token;
import com.demoapp.demo.HelperClass.UploadHelperClass;

public class ServicesHelperClass {

    String fullName, phoneNo, email, userName, password, date, gender, latitude, longitude, markerSnippet, markerName, customerNumberInfo, serviceCenterNumberInfo, message, rating, dateinfo, fullName_info, mail_info, request;
    Token token;


    UploadHelperClass uploads;
    public ServicesHelperClass() {}

    public ServicesHelperClass(String fullName, String phoneNo, String email, String userName, String password, String date, String gender, String latitude, String longitude, Token token) {
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.date = date;
        this.gender = gender;
        this.latitude = latitude;
        this.longitude = longitude;
        this.token = token;

    }

    public ServicesHelperClass(String markerSnippet, String markerName) {
        this.markerName = markerName;
        this.markerSnippet = markerSnippet;
    }

    public ServicesHelperClass(String customerNumberInfo, String serviceCenterNumberInfo,String fullName_info, String mail_info, String message ,String rating, String dateinfo) {
        this.customerNumberInfo = customerNumberInfo;
        this.serviceCenterNumberInfo = serviceCenterNumberInfo;
        this.fullName_info = fullName_info;
        this.mail_info = mail_info;
        this.message = message;
        this.rating = rating;
        this.dateinfo = dateinfo;
    }

    public ServicesHelperClass(String customerNumberInfo, String serviceCenterNumberInfo, String fullName_info, String mail_info, String message , String rating, String dateinfo, UploadHelperClass uploads) {
        this.customerNumberInfo = customerNumberInfo;
        this.serviceCenterNumberInfo = serviceCenterNumberInfo;
        this.fullName_info = fullName_info;
        this.mail_info = mail_info;
        this.message = message;
        this.rating = rating;
        this.dateinfo = dateinfo;
        this.uploads = uploads;
    }

    public ServicesHelperClass(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public ServicesHelperClass(String customerNumberInfo,String fullName_info, String infomessage, String on_pending_request) {
        this.customerNumberInfo = customerNumberInfo;
        this.message = infomessage;
        this.request = on_pending_request;
        this.fullName_info = fullName_info;
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

    public String getCustomerNumberInfo() {
        return customerNumberInfo;
    }

    public void setCustomerNumberInfo(String customerNumberInfo) {
        this.customerNumberInfo = customerNumberInfo;
    }

    public String getServiceCenterNumberInfo() {
        return serviceCenterNumberInfo;
    }

    public void setServiceCenterNumberInfo(String serviceCenterNumberInfo) {
        this.serviceCenterNumberInfo = serviceCenterNumberInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDateinfo() {
        return dateinfo;
    }

    public void setDateinfo(String dateinfo) {
        this.dateinfo = dateinfo;
    }

    public String getFullName_info() {
        return fullName_info;
    }

    public void setFullName_info(String fullName_info) {
        this.fullName_info = fullName_info;
    }

    public String getMail_info() {
        return mail_info;
    }

    public void setMail_info(String mail_info) {
        this.mail_info = mail_info;
    }

    public UploadHelperClass getUploads() {
        return uploads;
    }

    public void setUploads(UploadHelperClass uploads) {
        this.uploads = uploads;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
