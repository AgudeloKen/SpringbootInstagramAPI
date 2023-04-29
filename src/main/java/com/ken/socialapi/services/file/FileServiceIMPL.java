package com.ken.socialapi.services.file;

import com.cloudinary.Cloudinary;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Image;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
public class FileServiceIMPL implements IFileService {

    private final Cloudinary cloudinary;
    private final ImageRepository imageRepository;

    public FileServiceIMPL(Cloudinary cloudinary, ImageRepository imageRepository){
        this.cloudinary = cloudinary;
        this.imageRepository = imageRepository;
    }

    @Override
    public Image getUserFilePath(MultipartFile file, User user) throws UserException {
        String publicId = UUID.randomUUID().toString();

        Image image = new Image();
        try {
            if(user.getImage() != null){
                cloudinary.uploader().destroy(user.getImage().getPublicId(), Map.of());
                imageRepository.deleteByPublicId(user.getImage().getPublicId());
            }
            cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", publicId));
        }catch (IOException e){
            throw new UserException("Error uploading image.");
        }
        image.setPublicId(publicId);
        image.setImgURL(cloudinary.url().generate(publicId));
        user.setImage(image);
        return image;
    }

    @Override
    public Image getFilePath(MultipartFile file) throws UserException {
        String publicId = UUID.randomUUID().toString();

        Image image = new Image();
        try {
            cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", publicId));
        }catch (IOException e){
            throw new UserException("Error uploading image.");
        }
        image.setPublicId(publicId);
        image.setImgURL(cloudinary.url().generate(publicId));
        return image;
    }
}
