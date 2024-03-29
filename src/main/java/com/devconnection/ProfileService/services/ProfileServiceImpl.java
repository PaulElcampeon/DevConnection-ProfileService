package com.devconnection.ProfileService.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.CreateProfile;
import com.devconnection.ProfileService.messages.GenericMessage;
import com.devconnection.ProfileService.messages.UpdateProfileCurrentProjectsMessage;
import com.devconnection.ProfileService.messages.UpdateProfileDescriptionMessage;
import com.devconnection.ProfileService.messages.UpdateProfileExperienceMessage;
import com.devconnection.ProfileService.messages.UpdateProfileSkillsMessage;
import com.devconnection.ProfileService.repositories.ProfileRepository;
import com.mongodb.client.result.UpdateResult;

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
    public void createProfile(CreateProfile createProfile) {
        profileRepository.insert(new Profile(createProfile.getEmail(), createProfile.getUsername()));
    }

    @Override
    public Profile getProfile(GenericMessage genericMessage) {
        return profileRepository.findById(genericMessage.getEmail()).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public boolean updateProfileDescription(UpdateProfileDescriptionMessage updateProfileDescriptionMessage) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(updateProfileDescriptionMessage.getEmail()));

        Update update = new Update();
        update.set("description", updateProfileDescriptionMessage.getDescription());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Profile.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean updateProfileCurrentProjects(UpdateProfileCurrentProjectsMessage updateProfileCurrentProjects) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(updateProfileCurrentProjects.getEmail()));

        Update update = new Update();
        if (updateProfileCurrentProjects.isLeave()) {
            update.pull("currentProjects", updateProfileCurrentProjects.getProjectName());
        } else {
            update.addToSet("currentProjects", updateProfileCurrentProjects.getProjectName());
        }

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Profile.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean updateProfileExperience(UpdateProfileExperienceMessage updateProfileExperienceMessage) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(updateProfileExperienceMessage.getEmail()));

        Update update = new Update();
        update.set("yearsExperience", updateProfileExperienceMessage.getYearsExperience());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Profile.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean updateProfileSkills(UpdateProfileSkillsMessage updateProfileSkillsMessage) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(updateProfileSkillsMessage.getEmail()));

        Update update = new Update();
        if (updateProfileSkillsMessage.isRemove()) {
            update.pull("skills", updateProfileSkillsMessage.getSkill());
        } else {
            update.addToSet("skills", updateProfileSkillsMessage.getSkill());
        }

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Profile.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean profileExists(GenericMessage genericMessage) {
        return profileRepository.existsById(genericMessage.getEmail());
    }
}
