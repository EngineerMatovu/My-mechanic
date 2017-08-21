package com.example.android.io2014;

/**
 * Created by basasa-pc on 8/9/2017.
 */

public class Model {
    private String thumbnail;
    private String garagename;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    private String logitude;
    private String latitude;


    public Model(String thumbnail, String garagename, String specification, String service, String description, String contact, String location) {
        this.thumbnail = thumbnail;
        this.garagename = garagename;
        this.specification = specification;
        this.service = service;
        this.description = description;
        this.contact = contact;
        this.location = location;
    }

    public Model() {
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getGaragename() {
        return garagename;
    }

    public void setGaragename(String garagename) {
        this.garagename = garagename;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String specification;
    private String service;
    private String description;
    private String contact;
    private String location;
}
