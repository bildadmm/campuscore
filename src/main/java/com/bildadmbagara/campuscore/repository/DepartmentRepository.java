package com.bildadmbagara.campuscore.repository;

import com.bildadmbagara.campuscore.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByDepartmentCode(String departmentCode);

    boolean existsByDepartmentName(String departmentName);

    boolean existsByDepartmentCodeAndIdNot(String departmentCode, Long id);

    boolean existsByDepartmentNameAndIdNot(String departmentName, Long id);
}
