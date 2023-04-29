package com.ken.socialapi.services.story;

import com.ken.socialapi.exceptions.StoryException;
import com.ken.socialapi.exceptions.UserException;
import com.ken.socialapi.models.Story;
import com.ken.socialapi.models.User;
import com.ken.socialapi.repository.StoryRepository;
import com.ken.socialapi.repository.UserRepository;
import com.ken.socialapi.requests.StoryRequest;
import com.ken.socialapi.responses.StoryResponse;
import com.ken.socialapi.services.file.FileServiceIMPL;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoryServiceIMPL implements IStoryService{

    private final UserRepository userRepository;
    private final StoryRepository storyRepository;
    private final FileServiceIMPL fileServiceIMPL;

    public StoryServiceIMPL(UserRepository userRepository, StoryRepository storyRepository, FileServiceIMPL fileServiceIMPL){
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
        this.fileServiceIMPL = fileServiceIMPL;
    }
    @Override
    public List<Story> findAllByUser(Long id) throws UserException {
        if(!userRepository.existsById(id)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(id);
        return storyRepository.findAllByUser(user);
    }

    @Override
    public List<Story> findAll() {
        return storyRepository.findAll();
    }

    @Override
    public Story findOne(Long id) throws StoryException {
        if(!storyRepository.existsById(id)){
            throw new StoryException("Story not found.");
        }
        return storyRepository.getReferenceById(id);
    }

    @Override
    public Story createStory(Long userId, StoryRequest request) throws UserException {
        if(!userRepository.existsById(userId)){
            throw new UserException("User not found.");
        }
        User user = userRepository.getReferenceById(userId);
        Story story = new Story();
        story.setImage(fileServiceIMPL.getFilePath(request.getImage()));
        story.setUser(user);
        return storyRepository.save(story);
    }

    @Override
    public StoryResponse deleteStory(Long id) throws StoryException {
        if(!storyRepository.existsById(id)){
            throw new StoryException("Story not found.");
        }
        storyRepository.deleteById(id);
        return new StoryResponse("Story has been deleted.");
    }
}
