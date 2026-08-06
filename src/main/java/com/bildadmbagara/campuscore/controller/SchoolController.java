package com.bildadmbagara.campuscore.controller;

import com.bildadmbagara.campuscore.dto.CreateSchoolRequest;
import com.bildadmbagara.campuscore.dto.SchoolResponse;
import com.bildadmbagara.campuscore.dto.UpdateSchoolRequest;
import com.bildadmbagara.campuscore.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @PostMapping
    public ResponseEntity<SchoolResponse> createSchool(@Valid @RequestBody CreateSchoolRequest request) {
        SchoolResponse response = schoolService.createSchool(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public SchoolResponse getSchoolById(@PathVariable Long id)
    {
        return schoolService.getSchoolById(id);
    }

    @GetMapping
    public List<SchoolResponse> getAllSchools() {
        return schoolService.getAllSchools();
    }

    @PutMapping("/{id}")
    public SchoolResponse updateSchool(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSchoolRequest request) {

        return schoolService.updateSchool(id, request);
    }
}

