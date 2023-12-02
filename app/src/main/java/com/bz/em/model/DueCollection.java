package com.bz.em.model;

import java.util.Date;

public class DueCollection {

  // installmentNo
  // ":5,"
  // dueDate
  // ":"2020-08-01T00:00:00","
  // installmentAmount":8638.89

    private long installmentNo;

    private String dueDate;

    private double installmentAmount;

    public long getInstallmentNo() {
        return installmentNo;
    }

    public void setInstallmentNo(long installmentNo) {
        this.installmentNo = installmentNo;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public double getInstallmentAmount() {
        return installmentAmount;
    }

    public void setInstallmentAmount(double installmentAmount) {
        this.installmentAmount = installmentAmount;
    }
}
