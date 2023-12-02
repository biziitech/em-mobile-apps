package com.bz.em.model;

/**
 * Created by Ballal Hossain  12:55, 15-May-2020.
 **/
public class SamityMemberData {
    private Long id;
    private String nid;
    private String memberName;
    private String fatherHusbandName;
    //private LocalDate dateOfBirth;
    private String dateOfBirth;
    private String contactNo;
    private Long samityId;
    private Long bhbMemberId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getFatherHusbandName() {
        return fatherHusbandName;
    }

    public void setFatherHusbandName(String fatherHusbandName) {
        this.fatherHusbandName = fatherHusbandName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Long getSamityId() {
        return samityId;
    }

    public void setSamityId(Long samityId) {
        this.samityId = samityId;
    }

    public Long getBhbMemberId() {
        return bhbMemberId;
    }

    public void setBhbMemberId(Long bhbMemberId) {
        this.bhbMemberId = bhbMemberId;
    }
}
