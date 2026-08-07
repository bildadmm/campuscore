package com.bildadmbagara.campuscore.service;

import com.bildadmbagara.campuscore.dto.CreateDepartmentRequest;
import com.bildadmbagara.campuscore.dto.DepartmentResponse;
import com.bildadmbagara.campuscore.entity.Department;
import com.bildadmbagara.campuscore.entity.School;
import com.bildadmbagara.campuscore.exception.DepartmentNotFoundException;
import com.bildadmbagara.campuscore.exception.DuplicateDepartmentException;
import com.bildadmbagara.campuscore.exception.InactiveSchoolException;
import com.bildadmbagara.campuscore.exception.SchoolNotFoundException;
import com.bildadmbagara.campuscore.mapper.DepartmentMapper;
import com.bildadmbagara.campuscore.repository.DepartmentRepository;
import com.bildadmbagara.campuscore.repository.SchoolRepository;
import org.springframework.stereotype.Service;
import com.bildadmbagara.campuscore.dto.UpdateDepartmentRequest;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final SchoolRepository schoolRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            SchoolRepository schoolRepository,
            DepartmentMapper departmentMapper) {

        this.departmentRepository = departmentRepository;
        this.schoolRepository = schoolRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentResponse createDepartment(CreateDepartmentRequest request){

        String normalizedCode = request.getDepartmentCode()
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();

        String normalizedName = request.getDepartmentName()
                .trim();

        validateDuplicateDepartment(normalizedCode, normalizedName);

        School school = schoolRepository
                .findById(request.getSchoolId())
                .orElseThrow(() ->
                        new SchoolNotFoundException(
                                "School not found with ID: "
                                        + request.getSchoolId()
                        )
                );

        if(!school.isActive()){
            throw new InactiveSchoolException("Cannot create a department under an inactive school");
        }

        Department department = departmentMapper.toEntity(request);

        department.setDepartmentCode(normalizedCode);
        department.setDepartmentName(normalizedName);
        department.setSchool(school);
        department.setActive(true);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponse(savedDepartment);
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponse)
                .toList();
    }

    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        return departmentMapper.toResponse(department);
    }

    public void validateDuplicateDepartment(String departmentCode, String departmentName){
        if(departmentRepository.existsByDepartmentCode(departmentCode)){
            throw new DuplicateDepartmentException("Department code already exists: "+departmentCode);
        }
        if(departmentRepository.existsByDepartmentName(departmentName)){
            throw new DuplicateDepartmentException("Department name already exists: "+departmentName);
        }
    }

    private void validateDuplicateDepartmentForUpdate(
            String departmentCode,
            String departmentName,
            Long id) {

        if (departmentRepository
                .existsByDepartmentCodeAndIdNot(departmentCode, id)) {

            throw new DuplicateDepartmentException(
                    "Department code already exists: " + departmentCode
            );
        }

        if (departmentRepository
                .existsByDepartmentNameAndIdNot(departmentName, id)) {

            throw new DuplicateDepartmentException(
                    "Department name already exists: " + departmentName
            );
        }
    }

    public DepartmentResponse updateDepartment(
            Long id,
            UpdateDepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException(
                                "Department not found with id: " + id
                        )
                );

        String normalizedDepartmentCode = request.getDepartmentCode()
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();

        String normalizedDepartmentName =
                request.getDepartmentName().trim();

        validateDuplicateDepartmentForUpdate(
                normalizedDepartmentCode,
                normalizedDepartmentName,
                id
        );

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() ->
                        new SchoolNotFoundException(
                                "School not found with id: "
                                        + request.getSchoolId()
                        )
                );

        if (!school.isActive()) {
            throw new InactiveSchoolException(
                    "Cannot move a department to an inactive school"
            );
        }

        department.setDepartmentCode(normalizedDepartmentCode);
        department.setDepartmentName(normalizedDepartmentName);
        department.setSchool(school);

        Department updatedDepartment =
                departmentRepository.save(department);

        return departmentMapper.toResponse(updatedDepartment);
    }

}
