package com.bildadmbagara.campuscore.entity;

import jakarta.persistence.*;

@Entity
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String schoolCode;
    @Column(nullable = false, unique = true)
    private String schoolName;
    private boolean active;

    public School() {
    }

    public School(String schoolCode, String schoolName) {
        this.schoolCode = schoolCode;
        this.schoolName = schoolName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
