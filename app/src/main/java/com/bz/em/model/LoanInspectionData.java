package com.bz.em.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class LoanInspectionData {

    @SerializedName("id")
    private long id;
    @SerializedName("applicationDate")
    private String applicationDate;
    @SerializedName("applicationNo")
    private String applicationNo;
    @SerializedName("applicantName")
    private String applicantName;
    @SerializedName("samityId")
    private String samityId;
    @SerializedName("samityName")
    private String samityName;
    @SerializedName("basicCenterId")
    private String basicCenterId;
    @SerializedName("basicCenterName")
    private String basicCenterName;
    @SerializedName("districtName")
    private String districtName;
    @SerializedName("upzillaName")
    private String upzillaName;
    @SerializedName("unionName")
    private String unionName;
    @SerializedName("status")
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getSamityId() {
        return samityId;
    }

    public void setSamityId(String samityId) {
        this.samityId = samityId;
    }

    public String getSamityName() {
        return samityName;
    }

    public void setSamityName(String samityName) {
        this.samityName = samityName;
    }

    public String getBasicCenterId() {
        return basicCenterId;
    }

    public void setBasicCenterId(String basicCenterId) {
        this.basicCenterId = basicCenterId;
    }

    public String getBasicCenterName() {
        return basicCenterName;
    }

    public void setBasicCenterName(String basicCenterName) {
        this.basicCenterName = basicCenterName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getUpzillaName() {
        return upzillaName;
    }

    public void setUpzillaName(String upzillaName) {
        this.upzillaName = upzillaName;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
