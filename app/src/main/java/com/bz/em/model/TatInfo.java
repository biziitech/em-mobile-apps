package com.bz.em.model;

import com.google.gson.annotations.SerializedName;

public class TatInfo {

    private Long loanAccountId;
    private int noOfMachine;
    private Long weaverMachineTypeId;
    private String machineTypeName;

    public Long getLoanAccountId() {
        return loanAccountId;
    }

    public void setLoanAccountId(Long loanAccountId) {
        this.loanAccountId = loanAccountId;
    }

    public int getNoOfMachine() {
        return noOfMachine;
    }

    public void setNoOfMachine(int noOfMachine) {
        this.noOfMachine = noOfMachine;
    }

    public Long getWeaverMachineTypeId() {
        return weaverMachineTypeId;
    }

    public void setWeaverMachineTypeId(Long weaverMachineTypeId) {
        this.weaverMachineTypeId = weaverMachineTypeId;
    }

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }
}
