package com.example.orangehackathon.repository;

import com.example.orangehackathon.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByEmail(String email);
    boolean existsByEmail(String email);
}