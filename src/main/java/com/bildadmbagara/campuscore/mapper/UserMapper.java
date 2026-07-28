package com.bildadmbagara.campuscore.mapper;

import com.bildadmbagara.campuscore.dto.UserResponse;
import com.bildadmbagara.campuscore.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setActive(user.isActive());
        return response;
    }
}
