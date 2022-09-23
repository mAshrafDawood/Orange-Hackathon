package com.example.orangehackathon.utility;

import com.example.orangehackathon.dto.UserDTO;
import com.example.orangehackathon.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {
    public static String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public static UserDTO convertToDTO(User user){
        return new UserDTO(user.getId(), user.getEmail(), user.getName());
    }
}
