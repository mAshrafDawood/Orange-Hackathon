package com.example.orangehackathon.repository;

import com.example.orangehackathon.entity.Reel;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ReelRepository extends CrudRepository<Reel, Long> {
    Set<Reel> findReelsByUserId(Long id);
}