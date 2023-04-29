package com.ken.socialapi.services.story;

import com.ken.socialapi.exceptions.StoryException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Story;
import com.ken.socialapi.requests.StoryRequest;
import com.ken.socialapi.responses.StoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IStoryService {

    List<Story> findAllByUser(Long id) throws UserException;

    List<Story> findAll();

    Story findOne(Long id) throws StoryException;

    Story createStory(Long userId, StoryRequest request) throws UserException;

    StoryResponse deleteStory(Long id) throws StoryException;

}
