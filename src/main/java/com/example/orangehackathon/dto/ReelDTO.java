package com.example.orangehackathon.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReelDTO {
    Long Id;
    String video;
    String description;
    Set<UserDTO> Likes;
    UserDTO user;
}
