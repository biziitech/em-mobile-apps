package com.bz.em.model;

import java.util.ArrayList;
import java.util.List;

public class UserDtl {

    private boolean success;
    private List<UserDtl> offices = new ArrayList<>();
    private long id;
    private String userType;
    private String username;
    private String error;
    private String officeName;
    private String shortCode;
    private long officeId;


    public List<UserDtl> getOffices() {
        return offices;
    }

    public void setOffices(List<UserDtl> offices) {
        this.offices = offices;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
