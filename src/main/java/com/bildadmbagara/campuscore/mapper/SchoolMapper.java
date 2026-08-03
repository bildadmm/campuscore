package com.bildadmbagara.campuscore.mapper;

import com.bildadmbagara.campuscore.dto.CreateSchoolRequest;
import com.bildadmbagara.campuscore.dto.SchoolResponse;
import com.bildadmbagara.campuscore.entity.School;
import org.springframework.stereotype.Component;

@Component
public class SchoolMapper {
    public School toEntity(CreateSchoolRequest request){
        School school = new School();

        school.setSchoolCode(request.getSchoolCode());
        school.setSchoolName(request.getSchoolName());

        return school;
    }

    public SchoolResponse toResponse(School school){
        SchoolResponse response = new SchoolResponse();

        response.setSchoolCode(school.getSchoolCode());
        response.setSchoolName(school.getSchoolName());
        response.setId(school.getId());
        response.setActive(school.isActive());

        return response;

    }
}
