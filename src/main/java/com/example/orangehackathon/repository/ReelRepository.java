package com.example.orangehackathon.repository;

import com.example.orangehackathon.entity.Reel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ReelRepository extends JpaRepository<Reel, Long> {
    Iterable<Reel> findReelsByUserId(Long id);
    Iterable<Reel> findReelsByUserEmail(String email);

    @Query("SELECT r FROM Reel r JOIN r.like u WHERE 1=1")
    @EntityGraph(attributePaths = {"like"})
    Set <Reel> findAllReelsByUserId(@Param("id") Long id);

}