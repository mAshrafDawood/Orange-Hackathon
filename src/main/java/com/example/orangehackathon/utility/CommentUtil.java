package com.example.orangehackathon.utility;

import com.example.orangehackathon.dto.CommentDTO;
import com.example.orangehackathon.entity.Comment;

import java.util.HashSet;
import java.util.Set;

public class CommentUtil {
    public static CommentDTO convertToDTO(Comment comment){
        return new CommentDTO(
                comment.getId(),
                comment.getText()
        );
    }

    public static Set<CommentDTO> convertAllToDTO(Set<Comment> comments) {
        Set <CommentDTO> ret = new HashSet<>();
        for (Comment comment : comments){
            ret.add(convertToDTO(comment));
        }
        return ret;
    }
}
