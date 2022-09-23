package com.example.orangehackathon.service;

import com.example.orangehackathon.dto.CommentDTO;
import com.example.orangehackathon.entity.Comment;
import com.example.orangehackathon.entity.Reel;
import com.example.orangehackathon.entity.User;
import com.example.orangehackathon.repository.CommentRepository;
import com.example.orangehackathon.repository.ReelRepository;
import com.example.orangehackathon.repository.UserRepository;
import com.example.orangehackathon.utility.CommentUtil;
import com.example.orangehackathon.utility.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReelRepository reelRepository;

    public CommentDTO addComment(Long reelId, String commentText){
        Optional<Reel> optionalReel = reelRepository.findById(reelId);
        if (optionalReel.isEmpty()){
            return null;
        }
        User currUser = userRepository.findUserByEmail(UserUtil.getCurrentUsername());
        Reel reel = optionalReel.get();
        Comment temp = new Comment();
        temp.setUser(currUser);
        temp.setReel(reel);
        temp.setText(commentText);
        return CommentUtil.convertToDTO(commentRepository.save(temp));
    }

    public boolean deleteComment(Long commentId) {
        Optional <Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) return false;
        Comment currComment = optionalComment.get();
        if (currComment.getUser().equals(userRepository.findUserByEmail(UserUtil.getCurrentUsername()))){
            commentRepository.deleteById(commentId);
            return true;
        }
        return false;
    }

    public Set<CommentDTO> getCommentsOnReel(Long reelId) {
        return CommentUtil.convertAllToDTO(commentRepository.findCommentsByReelId(reelId));
    }
}
