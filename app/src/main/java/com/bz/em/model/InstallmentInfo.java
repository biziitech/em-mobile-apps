package com.bz.em.model;

public class InstallmentInfo {

    private String statusCode;
    private String statusDescEn;
    private String statusDescBn;
    private InstalmentDtl[] dataList;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescEn() {
        return statusDescEn;
    }

    public void setStatusDescEn(String statusDescEn) {
        this.statusDescEn = statusDescEn;
    }

    public String getStatusDescBn() {
        return statusDescBn;
    }

    public void setStatusDescBn(String statusDescBn) {
        this.statusDescBn = statusDescBn;
    }

    public InstalmentDtl[] getDataList() {
        return dataList;
    }

    public void setDataList(InstalmentDtl[] dataList) {
        this.dataList = dataList;
    }
}
