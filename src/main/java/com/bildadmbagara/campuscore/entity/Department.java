package com.bildadmbagara.campuscore.entity;

import jakarta.persistence.*;

@Entity
@Table(name="departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String departmentCode;
    @Column(nullable = false, unique = true)
    private String departmentName;
    private boolean active;
    @ManyToOne(optional = false)
    @JoinColumn(name="school-id",nullable = false)
    private School school;

    public Department() {
    }

    public Department(String departmentCode, String departmentName, School school) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.school = school;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}
