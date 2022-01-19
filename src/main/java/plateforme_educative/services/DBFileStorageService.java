package plateforme_educative.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.nio.file.Files;


import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import plateforme_educative.exception.FileStorageException;
import plateforme_educative.exception.MyFileNotFoundException;
import plateforme_educative.exception.ResourceNotFoundException;
import plateforme_educative.models.DBFile;
import plateforme_educative.models.Sub_thematics;
import plateforme_educative.models.Thematics;
import plateforme_educative.repositorys.DBFileRepository;
import plateforme_educative.repositorys.Sub_thematicsRepository;
import plateforme_educative.repositorys.ThematicsRepository;

@Service
public class DBFileStorageService {
	@Autowired
    private DBFileRepository dbFileRepository;
	@Autowired
	private Sub_thematicsRepository sub_thematicsRepository;
	@Autowired
    private ThematicsRepository thematicsRepository;
	
	 @Value("${files.path}")
	 private String filesPath;
    public DBFile storeFile(MultipartFile file,Long id) throws ResourceNotFoundException {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
            Sub_thematics t=sub_thematicsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("thematic not found"));
            dbFile.setSubthematic(t);
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String id) {
        return dbFileRepository.findById(id).get();
    }
    
    public List<DBFile> getFileBySubThematicId(Long thematicId) {
        return dbFileRepository.findBySubthematicId(thematicId);
    }
   
    public Resource download(String filename) {
    	try {
    		 Path file = Paths.get(filesPath)
                     .resolve(filename);
    		 Resource resource = new UrlResource(file.toUri());
    		 if ( resource.exists() ||  resource.isReadable()) {
                 return resource;
             } else {
                 throw new RuntimeException("Could not read the file!");
             }
    	}
    	catch(MalformedURLException e)
    	{
            throw new RuntimeException("Error: " + e.getMessage());
    	}
    }

		
}
