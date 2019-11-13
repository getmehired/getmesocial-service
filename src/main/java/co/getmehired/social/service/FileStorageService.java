package co.getmehired.social.service;

import co.getmehired.social.model.File;
import co.getmehired.social.repository.FileRepository;
import co.getmehired.social.util.AmazonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

	private static final Logger LOG = LoggerFactory.getLogger(FileStorageService.class);
	
	@Autowired
	private FileRepository fileRepository;


    public File storeFile(MultipartFile file) {
    	String orgFileName = file.getOriginalFilename();
    	String ext = orgFileName.substring(orgFileName.lastIndexOf('.'), orgFileName.length());
    	
    	String fileId = UUID.randomUUID().toString();
    	String name = UUID.randomUUID().toString() + ext;
    	String path = "album/" + name;
    	
    	boolean uploaded = AmazonUtil.uploadToAmazonS3(file, path);
    	if (uploaded) {
    		File f = new File(null, fileId, name, path, null);
    		File savedFile = fileRepository.save(f);
    		
    		if (savedFile != null) {
    			LOG.info("saved file: " + savedFile.getFileId());
    			return savedFile;
    		} else {
    			return null;
    		}
    	} else {
    		return null;
    	}
    }
    
    public Resource loadFileAsResource(String path) {
    	byte[] fileBytes = null;
		try {
			fileBytes = AmazonUtil.getFromAmazonS3(path);
		} catch (Exception e) {
			LOG.info("failed to get file: " + path);
			e.printStackTrace();
		}
		LOG.info("loading file: " + path);
    	return new ByteArrayResource(fileBytes);
    }

	public Optional<File> getFile(String fileId) {
		LOG.info("fetching data of file: " + fileId);
		return fileRepository.findByFileId(fileId);
	}

}