package com.example.orangehackathon.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CommentDTO {
    Long Id;
    String text;
}
