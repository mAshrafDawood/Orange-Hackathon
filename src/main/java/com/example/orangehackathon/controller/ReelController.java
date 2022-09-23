package com.example.orangehackathon.controller;

import com.example.orangehackathon.dto.ReelDTO;
import com.example.orangehackathon.dto.ReelUploadDTO;
import com.example.orangehackathon.service.ReelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/reel")
public class ReelController {

    @Autowired
    ReelService reelService;

    @GetMapping(value = "/get/user/{id}")
    public Set<ReelDTO> findUserReels(@PathVariable Long id) {
        return reelService.findUserReels(id);
    }

    @PostMapping(value = "/add")
    public ReelDTO uploadReel(@ModelAttribute ReelUploadDTO reelUploadDTO) {
        return reelService.uploadReel(reelUploadDTO.getVideo(), reelUploadDTO.getDescription());
    }

    @GetMapping(value = "/get/video")
    public Resource getVideo(@RequestBody String path){
        return reelService.getVideo(path);
    }

    @GetMapping(value = "/get")
    public Set<ReelDTO> getAllReels(){
        return reelService.getAllReels();
    }

    @GetMapping(value = "/get/{id}")
    public ReelDTO getReel(@PathVariable Long id){
        return reelService.getReel(id);
    }

    @GetMapping(value = "/my")
    public Set<ReelDTO> getMyReels(){
        return reelService.getMyReels();
    }

    @GetMapping(value = "/get/liked")
    public Set<ReelDTO> getMyLikes(){
        return reelService.getMyLikes();
    }

    @PostMapping(value = "like/{reelId}")
    public ResponseEntity<?> likeReel(@PathVariable Long reelId){
        return reelService.likeReel(reelId);
    }

    @DeleteMapping(value = "like/{reelId}")
    public ReelDTO removeLikeFromReel(@PathVariable Long reelId){
        return reelService.removeLikeFromReel(reelId);
    }
}
