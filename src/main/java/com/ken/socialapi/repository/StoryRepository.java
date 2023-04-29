package com.ken.socialapi.repository;

import com.ken.socialapi.models.Story;
import com.ken.socialapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    List<Story> findAllByUser(User user);
}
