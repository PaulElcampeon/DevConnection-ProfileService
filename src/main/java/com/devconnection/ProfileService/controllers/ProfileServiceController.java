package com.devconnection.ProfileService.controllers;

import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.*;
import com.devconnection.ProfileService.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileServiceController {

    private ProfileService profileService;

    @Autowired
    public ProfileServiceController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createProfile(@RequestBody GenericMessage genericMessage) {
        profileService.createProfile(genericMessage);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Profile getProfile(@RequestBody GenericMessage genericMessage) {
        return profileService.getProfile(genericMessage);
    }

    @RequestMapping(value = "/exists", method = RequestMethod.POST)
    public GenericResponse checkProfileExists(@RequestBody GenericMessage genericMessage) {
        return new GenericResponse(profileService.profileExists(genericMessage));
    }

    @RequestMapping(value = "/update/projects", method = RequestMethod.POST)
    public GenericResponse updateProjects(@RequestBody UpdateProfileCurrentProjectsMessage updateProfileCurrentProjectsMessage) {
        return new GenericResponse(profileService.updateProfileCurrentProjects(updateProfileCurrentProjectsMessage));
    }

    @RequestMapping(value = "/update/description", method = RequestMethod.POST)
    public GenericResponse updateDescription(@RequestBody UpdateProfileDescriptionMessage updateProfileDescriptionMessage) {
        return new GenericResponse(profileService.updateProfileDescription(updateProfileDescriptionMessage));
    }

    @RequestMapping(value = "/update/experience", method = RequestMethod.POST)
    public GenericResponse updateExperience(@RequestBody UpdateProfileExperienceMessage updateProfileExperienceMessage) {
        return new GenericResponse(profileService.updateProfileExperience(updateProfileExperienceMessage));
    }

    @RequestMapping(value = "/update/skills", method = RequestMethod.POST)
    public GenericResponse updateSkills(@RequestBody UpdateProfileSkillsMessage updateProfileSkillsMessage) {
        return new GenericResponse(profileService.updateProfileSkills(updateProfileSkillsMessage));
    }
}
