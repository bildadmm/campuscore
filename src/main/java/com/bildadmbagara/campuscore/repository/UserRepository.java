package com.bildadmbagara.campuscore.repository;

import com.bildadmbagara.campuscore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
