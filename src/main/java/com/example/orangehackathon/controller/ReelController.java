package com.example.orangehackathon.controller;

import com.example.orangehackathon.dto.ReelDTO;
import com.example.orangehackathon.dto.ReelUploadDTO;
import com.example.orangehackathon.entity.Reel;
import com.example.orangehackathon.service.ReelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

    @GetMapping(value = "/get/video", produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public Resource getVideo(@RequestBody String path){
        return reelService.getVideo(path);
    }

    @GetMapping(value = "/get")
    public Iterable<Reel> getAllReels(){
        return reelService.getAllReels();
    }

    @GetMapping(value = "/get/{id}")
    public Optional<Reel> getReel(@PathVariable Long id){
        return reelService.getReel(id);
    }

    @GetMapping(value = "/my")
    public Iterable<Reel> getMyReels(){
        return reelService.getMyReels();
    }
}
