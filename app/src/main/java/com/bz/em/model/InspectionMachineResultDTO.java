package com.bz.em.model;

import java.util.List;

public class InspectionMachineResultDTO {

    private List<InspectionMachineResult> inspectionMachineResultList;
    private Long loanApplicationId;
    private String remarks;
    private Long inspectionDateTime;
    private Long inspectorId;

    public List<InspectionMachineResult> getInspectionMachineResultList() {
        return inspectionMachineResultList;
    }

    public void setInspectionMachineResultList(List<InspectionMachineResult> inspectionMachineResultList) {
        this.inspectionMachineResultList = inspectionMachineResultList;
    }

    public Long getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(Long loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getInspectionDateTime() {
        return inspectionDateTime;
    }

    public void setInspectionDateTime(Long inspectionDateTime) {
        this.inspectionDateTime = inspectionDateTime;
    }

    public Long getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(Long inspectorId) {
        this.inspectorId = inspectorId;
    }
}
