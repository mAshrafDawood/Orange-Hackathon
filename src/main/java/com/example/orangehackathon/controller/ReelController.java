package com.example.orangehackathon.controller;

import com.example.orangehackathon.dto.ReelDTO;
import com.example.orangehackathon.dto.ReelUploadDTO;
import com.example.orangehackathon.entity.Reel;
import com.example.orangehackathon.service.ReelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/reel")
public class ReelController {

    @Autowired
    ReelService reelService;

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> findUserReels(@PathVariable Long id) {
        return reelService.findUserReels(id);
    }

    @PostMapping(value = "/add")
    public ReelDTO uploadVideo(@ModelAttribute ReelUploadDTO reelUploadDTO) {
        return reelService.uploadVideo(reelUploadDTO.getVideo(), reelUploadDTO.getDescription());
    }


}
