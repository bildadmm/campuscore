package com.bildadmbagara.campuscore.service;

import com.bildadmbagara.campuscore.dto.CreateSchoolRequest;
import com.bildadmbagara.campuscore.dto.SchoolResponse;
import com.bildadmbagara.campuscore.dto.UpdateSchoolRequest;
import com.bildadmbagara.campuscore.entity.School;
import com.bildadmbagara.campuscore.exception.DuplicateSchoolException;
import com.bildadmbagara.campuscore.exception.SchoolNotFoundException;
import com.bildadmbagara.campuscore.mapper.SchoolMapper;
import com.bildadmbagara.campuscore.repository.SchoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolmapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolmapper;
    }

    public SchoolResponse createSchool(CreateSchoolRequest request) {
        String normalizedSchoolCode = request
                .getSchoolCode()
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();
        String normalizedSchoolName = request
                .getSchoolName()
                .trim();
        validateDuplicateSchool(normalizedSchoolCode, normalizedSchoolName);
        School school = schoolMapper.toEntity(request);

        school.setSchoolCode(normalizedSchoolCode);
        school.setSchoolName(normalizedSchoolName);
        school.setActive(true);

        School savedSchool = schoolRepository.save(school);

        return schoolMapper.toResponse(savedSchool);
    }

    public SchoolResponse getSchoolById(Long id) {
        School school = schoolRepository
                .findById(id)
                .orElseThrow(() -> new SchoolNotFoundException("School not found " + id));
        return schoolMapper.toResponse(school);
    }

    public List<SchoolResponse> getAllSchools() {
        return schoolRepository
                .findAll()
                .stream()
                .map(schoolMapper::toResponse)
                .toList();
    }

    private void validateDuplicateSchool(String schoolCode, String schoolName) {
        if (schoolRepository.existsBySchoolCode(schoolCode)) {
            throw new DuplicateSchoolException("School code already exists");
        }
        if (schoolRepository.existsBySchoolName(schoolName)) {
            throw new DuplicateSchoolException("School name already exists");
        }
    }

    private void validateDuplicateSchoolForUpdate(String schoolCode, String schoolName, Long id) {
        if (schoolRepository.existsBySchoolCodeAndIdNot(schoolCode, id)) {
            throw
                    new DuplicateSchoolException("School Code already exists");
        }
        if (schoolRepository.existsBySchoolNameAndIdNot(schoolName, id)) {
            throw
                    new DuplicateSchoolException("School name already exists");
        }
    }

    public SchoolResponse updateSchool(
            Long id,
            UpdateSchoolRequest request) {

        School school = schoolRepository.findById(id)
                .orElseThrow(() ->
                        new SchoolNotFoundException(
                                "School not found with id: " + id
                        )
                );

        String normalizedSchoolCode = request.getSchoolCode()
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();

        String normalizedSchoolName =
                request.getSchoolName().trim();

        validateDuplicateSchoolForUpdate(
                normalizedSchoolCode,
                normalizedSchoolName,
                id
        );

        school.setSchoolCode(normalizedSchoolCode);
        school.setSchoolName(normalizedSchoolName);

        School updatedSchool =
                schoolRepository.save(school);

        return schoolMapper.toResponse(updatedSchool);
    }
}
