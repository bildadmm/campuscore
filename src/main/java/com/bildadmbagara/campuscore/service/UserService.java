package com.bildadmbagara.campuscore.service;

import com.bildadmbagara.campuscore.dto.CreateUserRequest;
import com.bildadmbagara.campuscore.dto.UpdateUserRequest;
import com.bildadmbagara.campuscore.dto.UserResponse;
import com.bildadmbagara.campuscore.entity.User;
import com.bildadmbagara.campuscore.enums.Role;
import com.bildadmbagara.campuscore.exception.DuplicateUserException;
import com.bildadmbagara.campuscore.exception.UserNotFoundException;
import com.bildadmbagara.campuscore.mapper.UserMapper;
import com.bildadmbagara.campuscore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public UserResponse saveUser(CreateUserRequest request) {
        validateDuplicateUser(request);
        User user = userMapper.toEntity(request);
        user.setRole(Role.STUDENT);
        user.setActive(true);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public List<UserResponse> getUsers() {
        List <User> users = userRepository.findAll();
        List <UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = userMapper.toResponse(user);
            responses.add(response);
        }
        return responses;
    }

    public UserResponse getUser(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);
        User user = optionalUser.orElseThrow(() -> new UserNotFoundException("User Not Found"));
        return userMapper.toResponse(user);
    }

    //update an existing user
    public UserResponse updateUser(
            Long id,
            UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found."));

        validateDuplicateUserForUpdate(id, request);

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    public String deleteUser(Long id){
        Optional<User> optionalUser =
                userRepository.findById(id);
        User user = optionalUser.orElseThrow(()->new UserNotFoundException("User Not Found"));
        userRepository.delete(user);
        return "User " + user.getFullName() + " deleted successfully.";
    }

    private void validateDuplicateUser(CreateUserRequest request){
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateUserException("Email already exists.");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUserException("Username already exists.");
        }
    }

    private void validateDuplicateUserForUpdate(
            Long id,
            UpdateUserRequest request) {

        Optional<User> emailUser =
                userRepository.findByEmail(request.getEmail());

        if (emailUser.isPresent()
                && !emailUser.get().getId().equals(id)) {

            throw new DuplicateUserException(
                    "Email already exists."
            );
        }

        Optional<User> usernameUser =
                userRepository.findByUsername(request.getUsername());

        if (usernameUser.isPresent()
                && !usernameUser.get().getId().equals(id)) {

            throw new DuplicateUserException(
                    "Username already exists."
            );
        }
    }
}

