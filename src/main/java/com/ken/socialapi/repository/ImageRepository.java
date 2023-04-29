package com.ken.socialapi.repository;

import com.ken.socialapi.models.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

    void deleteByPublicId(String publicId);
}
