package br.com.more_light.resource;

import br.com.more_light.service.S3Service;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/images")
@AllArgsConstructor
public class ImageResource {

    private final S3Service s3Service;

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> uploadImageS3(@RequestParam("file") MultipartFile file) {
        Map<String, Object> result = new HashMap<>();

        if (file == null || file.isEmpty()) {
            result.put("error", "file is missing or empty");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        try {
            String originalFilename = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();

            byte[] content = file.getBytes();

            // Calculate MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content);
            String md5 = String.format("%032x", new BigInteger(1, md.digest()));

            // Upload to S3 via service
            String key = s3Service.uploadFile(originalFilename, content, file.getContentType() == null ? "application/octet-stream" : file.getContentType());

            result.put("originalName", originalFilename);
            result.put("key", key);
            result.put("size", content.length);
            result.put("md5", md5);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            result.put("error", "I/O error: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            result.put("error", "unexpected error: " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<byte[]> getImageS3(@RequestParam String key) {
        byte[] imageBytes = s3Service.downloadFile(key);

        if (imageBytes == null || imageBytes.length == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        String contentType = URLConnection.guessContentTypeFromName(key);
        if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentLength(imageBytes.length);
        String filename = Paths.get(key).getFileName().toString();
        headers.setContentDispositionFormData("inline", filename);

        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}


