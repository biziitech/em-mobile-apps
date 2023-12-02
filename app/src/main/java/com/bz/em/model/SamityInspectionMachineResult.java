package com.bz.em.model;

public class SamityInspectionMachineResult {

    private Long memberId;
    private Long machineTypeId;
    private int noOfMachine;
    private String remarks;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMachineTypeId() {
        return machineTypeId;
    }

    public void setMachineTypeId(Long machineTypeId) {
        this.machineTypeId = machineTypeId;
    }

    public int getNoOfMachine() {
        return noOfMachine;
    }

    public void setNoOfMachine(int noOfMachine) {
        this.noOfMachine = noOfMachine;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
