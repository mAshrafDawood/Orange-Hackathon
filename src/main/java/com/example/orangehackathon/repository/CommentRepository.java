package com.example.orangehackathon.repository;

import com.example.orangehackathon.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    Set <Comment> findCommentsByReelId(Long id);
}