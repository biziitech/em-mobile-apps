package com.bz.em.model;

import java.util.List;

public class SamityInspectionMachineResultDTO {

    private List<SamityInspectionMachineResult> samityInspectionMachineResultList;
    private Long samityId;
    private String remarks;
    private Long inspectionDateTime;
    private Long inspectorId;
    private Long memberId;

    public List<SamityInspectionMachineResult> getSamityInspectionMachineResultList() {
        return samityInspectionMachineResultList;
    }

    public void setSamityInspectionMachineResultList(List<SamityInspectionMachineResult> samityInspectionMachineResultList) {
        this.samityInspectionMachineResultList = samityInspectionMachineResultList;
    }

    public Long getSamityId() {
        return samityId;
    }

    public void setSamityId(Long samityId) {
        this.samityId = samityId;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
