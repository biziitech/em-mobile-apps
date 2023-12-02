package com.bz.em.model;

public class InspectionMachineResult {
        private Long loanApplicationId;
        private Long machineTypeId;
        private int noOfMachine;
        private String remarks;


    public Long getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(Long loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
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
