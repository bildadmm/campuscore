package com.bildadmbagara.campuscore.repository;

import com.bildadmbagara.campuscore.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long> {
    boolean existsBySchoolCode(String schoolCode);

    boolean existsBySchoolName(String schoolName);
}
