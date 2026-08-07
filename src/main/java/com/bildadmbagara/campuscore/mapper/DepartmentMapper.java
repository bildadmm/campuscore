package com.bildadmbagara.campuscore.mapper;

import com.bildadmbagara.campuscore.dto.CreateDepartmentRequest;
import com.bildadmbagara.campuscore.dto.DepartmentResponse;
import com.bildadmbagara.campuscore.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    public Department toEntity(CreateDepartmentRequest request){
        Department department = new Department();
        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        return department;
    }

    public DepartmentResponse toResponse(Department department){
        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setDepartmentCode(department.getDepartmentCode());
        response.setDepartmentName(department.getDepartmentName());
        response.setActive(department.isActive());

        response.setSchoolId(department.getSchool().getId());
        response.setSchoolCode(department.getSchool().getSchoolCode());
        response.setSchoolName(department.getSchool().getSchoolName());

        return response;
    }
}
