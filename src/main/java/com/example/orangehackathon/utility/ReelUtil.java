package com.example.orangehackathon.utility;

import com.example.orangehackathon.dto.ReelDTO;
import com.example.orangehackathon.dto.UserDTO;
import com.example.orangehackathon.entity.Reel;
import com.example.orangehackathon.entity.User;

import java.util.HashSet;
import java.util.Set;

public class ReelUtil {
    public static ReelDTO convertToDTO(Reel reel){
        Set<User> like = reel.getLikes();
        Set<UserDTO> likeDTO = new HashSet<>();
        if (like != null){
            for (User user : like){
                likeDTO.add(UserUtil.convertToDTO(user));
            }
        }
        return new ReelDTO(
                reel.getId(),
                reel.getVideo()
                , reel.getDescription(),
                likeDTO,
                UserUtil.convertToDTO(reel.getUser())
        );
    }

    public static Set <ReelDTO> convertAllToDTO(Iterable<Reel> reels){
        Set <ReelDTO> ret = new HashSet<>();
        for (Reel reel : reels){
            ret.add(convertToDTO(reel));
        }
        return ret;
    }
}
