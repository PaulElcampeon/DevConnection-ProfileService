package com.devconnection.ProfileService.services;

import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.*;

public interface ProfileService {

    void createProfile(CreateProfileMessage createProfileMessage);

    Profile getProfile(String id);

    boolean updateProfileDescription(UpdateProfileDescriptionMessage updateProfileDescriptionMessage);

    boolean updateProfileCurrentProjects(UpdateProfileCurrentProjectsMessage updateProfileCurrentProjects);

    boolean updateProfileExperience(UpdateProfileExperienceMessage updateProfileExperienceMessage);

    boolean updateProfileSkills(UpdateProfileSkillsMessage updateProfileSkillsMessage);

    void removeProfile(String id);
}
