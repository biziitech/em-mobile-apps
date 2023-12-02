package com.bz.em.model;

public class SamityMemberMachineDetailData {

    private Long memberId;
    private Long machineTypeId;
    private String machineTypeName;
    private int noOfMachine;

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

    public String getMachineTypeName() {
        return machineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        this.machineTypeName = machineTypeName;
    }

    public int getNoOfMachine() {
        return noOfMachine;
    }

    public void setNoOfMachine(int noOfMachine) {
        this.noOfMachine = noOfMachine;
    }
}
