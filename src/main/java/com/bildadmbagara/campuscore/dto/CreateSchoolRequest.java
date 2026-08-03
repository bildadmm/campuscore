package com.bildadmbagara.campuscore.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateSchoolRequest {
    @NotBlank(message = "School code must not be blank")
    private String schoolCode;
    @NotBlank(message ="School name must not be blank")
    private String schoolName;

    public CreateSchoolRequest() {
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
