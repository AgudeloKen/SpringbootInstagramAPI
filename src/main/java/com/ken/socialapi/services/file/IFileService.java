package com.ken.socialapi.services.file;

import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Image;
import com.ken.socialapi.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IFileService {

    Image getUserFilePath(MultipartFile file, User user) throws UserException;

    Image getFilePath(MultipartFile file) throws UserException;

}
