package co.getmehired.social.rest;

import co.getmehired.social.exception.ResourceNotFoundException;
import co.getmehired.social.model.File;
import co.getmehired.social.service.FileStorageService;
import co.getmehired.social.util.AmazonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/files")
public class FileResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileResource.class);

	@Autowired
	private FileStorageService fileStorageService;
	
    @PostMapping("/upload")
    public File uploadFile(
    		@RequestParam("file") MultipartFile file) {
        File uploadedFile = fileStorageService.storeFile(file);

        return uploadedFile;
    }
    
    @GetMapping("/show/{fileId}")
    public void showFile(
    		@PathVariable String fileId,
    		HttpServletResponse response) throws Exception {
    	File file = fileStorageService.getFile(fileId).orElseThrow(() -> new ResourceNotFoundException("Invalid FileId"));

        if (AmazonUtil.existsInAmazonS3(file.getPath())) {
            byte[] b = AmazonUtil.getFromAmazonS3(file.getPath());
            response.setContentType("image/jpeg");
            response.getOutputStream().write(b);
            response.getOutputStream().flush();
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) {
    	File file = fileStorageService.getFile(fileId).orElseThrow(() -> new ResourceNotFoundException("Invalid FileId"));
    	
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(file.getPath());

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOG.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
    
}
