package com.example.orangehackathon.service;

import com.example.orangehackathon.dto.ReelDTO;
import com.example.orangehackathon.dto.UserDTO;
import com.example.orangehackathon.entity.Reel;
import com.example.orangehackathon.repository.ReelRepository;
import com.example.orangehackathon.repository.UserRepository;
import com.example.orangehackathon.utility.FileUploadUtil;
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

            UserDTO retUser = new UserDTO();
            retUser.setId(reel.getUser().getId());
            retUser.setEmail(reel.getUser().getEmail());
            retUser.setName(reel.getUser().getName());

            ReelDTO ret = new ReelDTO(reel.getId(), reel.getVideo(), reel.getDescription(), retUser);
            return ret;
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

    public Iterable<Reel> getAllReels(){
        return reelRepository.findAll();
    }

    public Optional<Reel> getReel(Long id){
        return reelRepository.findById(id);
    }

    public Iterable<Reel> getMyReels(){
        String username = UserUtil.getCurrentUsername();
        return reelRepository.findReelsByUserEmail(username);
    }
}
