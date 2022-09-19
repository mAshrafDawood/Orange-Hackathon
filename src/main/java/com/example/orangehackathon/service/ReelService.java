package com.example.orangehackathon.service;

import com.example.orangehackathon.dto.ReelDTO;
import com.example.orangehackathon.dto.UserDTO;
import com.example.orangehackathon.entity.Reel;
import com.example.orangehackathon.repository.ReelRepository;
import com.example.orangehackathon.repository.UserRepository;
import com.example.orangehackathon.utility.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
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
        Set<Reel> reels = reelRepository.findReelsByUserId(id);
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
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else {
                username = principal.toString();
            }

            reel.setUser(userRepository.findUserByEmail(username));
            reel = reelRepository.save(reel);

            UserDTO retUser = new UserDTO();
            retUser.setId(reel.getUser().getId());
            retUser.setEmail(reel.getUser().getEmail());
            retUser.setName(reel.getUser().getName());

            ReelDTO ret = new ReelDTO(reel.getId(), reel.getVideo(), reel.getDescription(), retUser);
            return ret;
        }
    }
}
