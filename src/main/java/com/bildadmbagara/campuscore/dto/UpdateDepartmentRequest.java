package com.bildadmbagara.campuscore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateDepartmentRequest {

    @NotBlank(message = "Department code must not be blank")
    private String departmentCode;

    @NotBlank(message = "Department name must not be blank")
    private String departmentName;

    @NotNull(message = "School ID is required")
    private Long schoolId;

    public UpdateDepartmentRequest() {
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
}