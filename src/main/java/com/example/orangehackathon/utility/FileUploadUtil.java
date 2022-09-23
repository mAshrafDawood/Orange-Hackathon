package com.example.orangehackathon.utility;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Scanner;

public class FileUploadUtil {
    public static boolean saveFile(String uploadDir, String fileName, MultipartFile multipartFile){
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException io) {
                io.printStackTrace();
                return false;
            }
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            StringBuilder dataBuilder = new StringBuilder();
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNext()) dataBuilder.append(scanner.next());
            String data = Base64.getEncoder().encodeToString(dataBuilder.toString().getBytes());

            Files.write(filePath, data.getBytes());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return false;
        }
        return true;
    }
}
