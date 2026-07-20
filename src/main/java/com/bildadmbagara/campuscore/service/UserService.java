package com.bildadmbagara.campuscore.service;

import com.bildadmbagara.campuscore.dto.CreateUserRequest;
import com.bildadmbagara.campuscore.dto.UserResponse;
import com.bildadmbagara.campuscore.entity.User;
import com.bildadmbagara.campuscore.enums.Role;
import com.bildadmbagara.campuscore.exception.UserNotFoundException;
import com.bildadmbagara.campuscore.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
   private final UserRepository userRepository;

   public UserService(UserRepository userRepository){
       this.userRepository=userRepository;
   }

   public UserResponse saveUser(CreateUserRequest request) {
    User user = new User();
    user.setFullName(request.getFullName());
    user.setEmail(request.getEmail());
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setRole(Role.STUDENT);
    user.setActive(true);
    userRepository.save(user);
    UserResponse response = new UserResponse();
    response.setId(user.getId());
    response.setFullName(user.getFullName());
    response.setEmail(user.getEmail());
    response.setUsername(user.getUsername());
    response.setRole(user.getRole());
    response.setActive(user.isActive());
    return response;
   }

   public List<User> getAllUsers(){
       return userRepository.findAll();
    }

    public User getUser(Long id){
        Optional<User> result = userRepository.findById(id);
        if(result.isPresent()){
            return result.get();
        }
        throw new UserNotFoundException("User with ID "+id+" was not found.");
    }


}
