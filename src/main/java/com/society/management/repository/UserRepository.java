package com.society.management.repository;

import com.society.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email, String password);
    User findByResidentId(Integer residentId);

}