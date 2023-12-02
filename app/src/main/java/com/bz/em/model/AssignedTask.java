package com.bz.em.model;
/**
 * Created by Bellal Hossain
 **/
public class AssignedTask {

    private String nidNo;
    private String memberName;
    private String fatherName;
    private String address;
    private String mobileNo;
    private String inspectionDate;

    public AssignedTask(String nidNo, String memberName, String fatherName, String address, String mobileNo, String inspectionDate) {
        this.nidNo = nidNo;
        this.memberName = memberName;
        this.fatherName = fatherName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.inspectionDate = inspectionDate;
    }

    public String getNidNo() {
        return nidNo;
    }

    public void setNidNo(String nidNo) {
        this.nidNo = nidNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }
}
