package ku.cs.restaurant.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    public String saveImage(String folderPath, MultipartFile image) throws IOException {
        Files.createDirectories(Paths.get(folderPath));
        String originalFileName = image.getOriginalFilename();
        if (originalFileName.contains("..") || originalFileName.contains("/") || originalFileName.contains("\\")) {
            throw new IllegalArgumentException("Invalid filename");
        }
        String fileName = System.currentTimeMillis() + "_" + originalFileName;
        Path path = Paths.get(folderPath, fileName).normalize().toAbsolutePath();
        Path folder = Paths.get(folderPath).normalize().toAbsolutePath();
        if (!path.startsWith(folder)) {
            throw new IllegalArgumentException("Invalid filename");
        }
        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }
}
