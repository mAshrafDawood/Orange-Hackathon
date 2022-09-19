package com.example.orangehackathon.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ReelDTO {
    Long Id;
    String video;
    String description;
    UserDTO user;
}
