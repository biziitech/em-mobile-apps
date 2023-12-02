package com.bz.em.model;

public class LoanInfoDtl {

    private Long weaverId;
    private String projectName;
    private int machineTypeForLoan;
    private String machineTypeName;
    private double loanAmount;
    private int noOfInstallment;
    private double interestRate;
    private double chargeRate;
    private int gracePeriod;
    private Long loanId;
    private String applicationStatus;//new added 14.08.2020

    public Long getWeaverId() {
        return weaverId;
    }

    public void setWeaverId(Long weaverId) {
        this.weaverId = weaverId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getMachineTypeForLoan() {
        return machineTypeForLoan;
    }

    public void setMachineTypeForLoan(int machineTypeForLoan) {
        this.machineTypeForLoan = machineTypeForLoan;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getNoOfInstallment() {
        return noOfInstallment;
    }

    public void setNoOfInstallment(int noOfInstallment) {
        this.noOfInstallment = noOfInstallment;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(double chargeRate) {
        this.chargeRate = chargeRate;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(int gracePeriod) {
        this.gracePeriod = gracePeriod;
    }


    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }
}
