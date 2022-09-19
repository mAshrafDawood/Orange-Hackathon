package com.example.orangehackathon.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReelUploadDTO {
    MultipartFile video;
    String description;
}
