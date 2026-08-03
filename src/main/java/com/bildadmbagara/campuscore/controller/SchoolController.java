package com.bildadmbagara.campuscore.controller;

import com.bildadmbagara.campuscore.dto.CreateSchoolRequest;
import com.bildadmbagara.campuscore.dto.SchoolResponse;
import com.bildadmbagara.campuscore.service.SchoolService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

