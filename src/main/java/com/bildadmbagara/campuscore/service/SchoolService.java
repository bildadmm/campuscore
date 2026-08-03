package com.bildadmbagara.campuscore.service;

import com.bildadmbagara.campuscore.dto.CreateSchoolRequest;
import com.bildadmbagara.campuscore.dto.SchoolResponse;
import com.bildadmbagara.campuscore.entity.School;
import com.bildadmbagara.campuscore.exception.DuplicateSchoolException;
import com.bildadmbagara.campuscore.mapper.SchoolMapper;
import com.bildadmbagara.campuscore.repository.SchoolRepository;
import org.springframework.stereotype.Service;
@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolmapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolmapper) {
        this.schoolRepository = schoolRepository;
        this.schoolmapper = schoolmapper;
    }

    public SchoolResponse createSchool(CreateSchoolRequest request){
        String normalizedSchoolCode = request
                .getSchoolCode()
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();
        String normalizedSchoolName = request
                .getSchoolName()
                .trim();
        validateDuplicateSchool(normalizedSchoolCode, normalizedSchoolName);
        School school = schoolmapper.toEntity(request);

        school.setSchoolCode(normalizedSchoolCode);
        school.setSchoolName(normalizedSchoolName);
        school.setActive(true);

        School savedSchool = schoolRepository.save(school);

        return schoolmapper.toResponse(savedSchool);
    }

    private void validateDuplicateSchool(String schoolCode, String schoolName){
        if(schoolRepository.existsBySchoolCode(schoolCode)){
            throw new DuplicateSchoolException("School code already exists");
        }
        if(schoolRepository.existsBySchoolName(schoolName)){
            throw new DuplicateSchoolException("School name already exists");
        }
    }
}
