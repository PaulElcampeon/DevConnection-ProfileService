package com.devconnection.ProfileService.services;

import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.*;
import com.devconnection.ProfileService.repositories.ProfileRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;

    private MongoTemplate mongoTemplate;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, MongoTemplate mongoTemplate) {
        this.profileRepository = profileRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createProfile(CreateProfileMessage createProfileMessage) {
        profileRepository.insert(new Profile(createProfileMessage.getId()));
    }

    @Override
    public Profile getProfile(String id) {
        return profileRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public boolean updateProfileDescription(UpdateProfileDescriptionMessage updateProfileDescriptionMessage) {
        Query query = new Query();
        query.addCriteria(Criteria
                .where("_id").lt(updateProfileDescriptionMessage.getId()));
        Update update = new Update();
        update.set("description", updateProfileDescriptionMessage.getDescription());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Profile.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean updateProfileCurrentProjects(UpdateProfileCurrentProjectsMessage updateProfileCurrentProjects) {
        return false;
    }

    @Override
    public boolean updateProfileExperience(UpdateProfileExperienceMessage updateProfileExperienceMessage) {
        return false;
    }

    @Override
    public boolean updateProfileSkills(UpdateProfileSkillsMessage updateProfileSkillsMessage) {
        return false;
    }

    @Override
    public void removeProfile(String id) {
        profileRepository.deleteById(id);
    }
}
