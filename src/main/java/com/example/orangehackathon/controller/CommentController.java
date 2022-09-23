package com.example.orangehackathon.controller;

import com.example.orangehackathon.dto.CommentDTO;
import com.example.orangehackathon.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @PostMapping("/add/{reelId}")
    public CommentDTO addComment(@PathVariable Long reelId, @RequestBody String commentText){
        return commentService.addComment(reelId, commentText);
    }

    @DeleteMapping("/delete/{commentId}")
    public boolean deleteComment(@PathVariable Long commentId){
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/reel/{reelId}")
    public Set<CommentDTO> getCommentsOnReel(@PathVariable Long reelId){
        return commentService.getCommentsOnReel(reelId);
    }
}
