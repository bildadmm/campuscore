package com.bildadmbagara.campuscore.controller;

import com.bildadmbagara.campuscore.dto.CreateUserRequest;
import com.bildadmbagara.campuscore.dto.UpdateUserRequest;
import com.bildadmbagara.campuscore.dto.UserResponse;
import com.bildadmbagara.campuscore.entity.User;
import com.bildadmbagara.campuscore.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService=userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody CreateUserRequest request)
    {
        return userService.saveUser(request);
    }


    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id)
    {
        return userService.getUser(id);
    }

    @GetMapping()
    public List<UserResponse> getUsers(){
        return userService.getUsers();
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        return userService.updateUser(id,request);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    }



