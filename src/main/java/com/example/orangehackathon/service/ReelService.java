package com.example.orangehackathon.service;

import com.example.orangehackathon.dto.ReelDTO;
import com.example.orangehackathon.dto.UserDTO;
import com.example.orangehackathon.entity.Reel;
import com.example.orangehackathon.entity.User;
import com.example.orangehackathon.exceptions.ErrorResponse;
import com.example.orangehackathon.exceptions.Errors;
import com.example.orangehackathon.repository.ReelRepository;
import com.example.orangehackathon.repository.UserRepository;
import com.example.orangehackathon.utility.FileUploadUtil;
import com.example.orangehackathon.utility.ReelUtil;
import com.example.orangehackathon.utility.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Set;

import static org.springframework.util.StringUtils.cleanPath;

@Service
public class ReelService {

    private static final String STATIC_DIR = "src/main/resources/static";
    @Autowired
    ReelRepository reelRepository;

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<?> findUserReels(Long id) {
        Iterable<Reel> reels = reelRepository.findReelsByUserId(id);
        ResponseEntity<Reel> ret = new ResponseEntity(reels, HttpStatus.OK);
        return ret;
    }

    public ReelDTO uploadVideo(MultipartFile video, String description) {
        String fileName = cleanPath(video.getOriginalFilename()).replace(" ", "-");

        if (!FileUploadUtil.saveFile(STATIC_DIR, fileName, video)) {
            return null;
        } else {
            Path uploadPath = Paths.get(STATIC_DIR + "/" + fileName);
            Reel reel = new Reel();
            reel.setDescription(description);
            reel.setVideo(uploadPath.toString());
            String username = UserUtil.getCurrentUsername();
            reel.setUser(userRepository.findUserByEmail(username));
            reel = reelRepository.save(reel);
            return ReelUtil.convertToDTO(reel);
        }
    }

    public Resource getVideo(String path){
        Path file = Paths.get(path);
        try {
            Resource resource = new UrlResource(file.toUri());
            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("File is corrupted");
        }
    }

    public Set<ReelDTO> getAllReels(){
        return ReelUtil.convertAllToDTO(reelRepository.findAll());
    }

    public ReelDTO getReel(Long id){

        Optional <Reel> optionalReel =  reelRepository.findById(id);
        if (optionalReel.isEmpty()) return null;
        return ReelUtil.convertToDTO(optionalReel.get());
    }

    public Iterable<ReelDTO> getMyReels(){
        String username = UserUtil.getCurrentUsername();
        return ReelUtil.convertAllToDTO(reelRepository.findReelsByUserEmail(username));
    }

    public Set<ReelDTO> getMyLikes(){
        User currUser = userRepository.findUserByEmail(UserUtil.getCurrentUsername());
        Set <Reel> ans = reelRepository.findAllReelsByUserId(currUser.getId());
        return ReelUtil.convertAllToDTO(ans);
    }

    public ResponseEntity<?> likeReel(Long reelId){
        Optional<Reel> optionalReel = reelRepository.findById(reelId);
        if (optionalReel.isEmpty()) return new ResponseEntity<>(new ErrorResponse(Errors.REEL_IS_MISSING.getCode(), Errors.REEL_IS_MISSING.getMessage()), HttpStatus.BAD_REQUEST);
        Reel reel = optionalReel.get();
        User currUser = userRepository.findUserByEmail(UserUtil.getCurrentUsername());
        if (currUser.getLike().contains(reel)) return new ResponseEntity<>(ReelUtil.convertToDTO(reel), HttpStatus.OK);
        currUser.getLike().add(reel);
        reel.getLike().add(currUser);
        userRepository.save(currUser);
        return new ResponseEntity<>(ReelUtil.convertToDTO(reelRepository.save(reel)), HttpStatus.OK);
    }

    public ReelDTO removeLikeFromReel(Long reelId){
        Optional<Reel> optionalReel = reelRepository.findById(reelId);
        if (optionalReel.isEmpty()) return null;
        Reel reel = optionalReel.get();
        User currUser = userRepository.findUserByEmail(UserUtil.getCurrentUsername());
        reel.getLike().remove(currUser);
        currUser.getLike().remove(reel);
        userRepository.save(currUser);
        return ReelUtil.convertToDTO(reelRepository.save(reelRepository.save(reel)));
    }
}
