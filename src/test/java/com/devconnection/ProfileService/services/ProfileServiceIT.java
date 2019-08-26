package com.devconnection.ProfileService.services;

import com.devconnection.ProfileService.ProfileServiceApplication;
import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.*;
import com.devconnection.ProfileService.repositories.ProfileRepository;
import com.devconnection.ProfileService.services.ProfileService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProfileServiceApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ProfileServiceIT {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @After
    public void tearDown() {
        profileRepository.deleteAll();
    }

    @Test
    public void createProfile() {
        String email = "Dave@live.co.uk";
        GenericMessage genericMessage = new GenericMessage();
        genericMessage.setId(email);

        profileService.createProfile(genericMessage);

        assertTrue(profileRepository.existsById(email));
    }

    @Test
    public void getProfile() {
        String email = "Dave@live.co.uk";
        Profile profile = new Profile(email);
        GenericMessage genericMessage = new GenericMessage();
        genericMessage.setId(email);

        profileRepository.insert(profile);

        assertEquals(profile, profileService.getProfile(genericMessage));
    }

    @Test
    public void updateProfileDescription() {
        String email = "Dave@live.co.uk";

        Profile profile = new Profile();
        profile.setId(email);
        profile.setDescription("bye bye");
        profileRepository.insert(profile);

        String newDescription = "hello";
        UpdateProfileDescriptionMessage message = new UpdateProfileDescriptionMessage();
        message.setId(email);
        message.setDescription(newDescription);

        boolean updated = profileService.updateProfileDescription(message);

        Profile retrievedProfile = profileRepository.findById(email).get();

        assertTrue(updated);
        assertEquals(newDescription, retrievedProfile.getDescription());
    }

    @Test
    public void updateProfileCurrentProjects_addingToProjects() {
        String email = "Dave@live.co.uk";

        Profile profile = new Profile();
        profile.setId(email);
        profileRepository.insert(profile);

        String projectName = "Glorbium";

        UpdateProfileCurrentProjectsMessage message = new UpdateProfileCurrentProjectsMessage();
        message.setId(email);
        message.setLeave(false);
        message.setProjectName(projectName);

        boolean updated = profileService.updateProfileCurrentProjects(message);

        Profile retrievedProfile = profileRepository.findById(email).get();

        assertTrue(updated);
        assertTrue(retrievedProfile.getCurrentProjects().contains(projectName));
    }

    @Test
    public void updateProfileCurrentProjects_removingFromProjects() {
        String email = "Dave@live.co.uk";

        Profile profile = new Profile();
        profile.setId(email);
        profile.getCurrentProjects().add("Pets Online");
        profile.getCurrentProjects().add("Ford Corp");
        profileRepository.insert(profile);

        String projectName = "Pets Online";

        UpdateProfileCurrentProjectsMessage message = new UpdateProfileCurrentProjectsMessage();
        message.setId(email);
        message.setLeave(true);
        message.setProjectName(projectName);

        boolean updated = profileService.updateProfileCurrentProjects(message);

        Profile retrievedProfile = profileRepository.findById(email).get();

        assertTrue(updated);
        assertEquals(Arrays.asList("Ford Corp"), retrievedProfile.getCurrentProjects());
    }

    @Test
    public void updateProfileExperience() {
        String email = "Dave@live.co.uk";

        Profile profile = new Profile();
        profile.setId(email);
        profile.setYearsExperience(1);
        profileRepository.insert(profile);

        int newExperience = 2;
        UpdateProfileExperienceMessage message = new UpdateProfileExperienceMessage();
        message.setId(email);
        message.setYearsExperience(newExperience);

        boolean updated = profileService.updateProfileExperience(message);

        Profile retrievedProfile = profileRepository.findById(email).get();

        assertTrue(updated);
        assertEquals(newExperience, retrievedProfile.getYearsExperience());
    }

    @Test
    public void updateProfileSkills_addingSkill() {
        String email = "Dave@live.co.uk";

        Profile profile = new Profile();
        profile.setId(email);
        profile.getSkills().addAll(Arrays.asList("Java", "Docker"));
        profileRepository.insert(profile);

        String newSkill = "React";
        UpdateProfileSkillsMessage message = new UpdateProfileSkillsMessage();
        message.setId(email);
        message.setRemove(false);
        message.setSkill(newSkill);

        boolean updated = profileService.updateProfileSkills(message);

        Profile retrievedProfile = profileRepository.findById(email).get();

        assertTrue(updated);
        assertTrue(retrievedProfile.getSkills().contains(newSkill));
        assertEquals(3, retrievedProfile.getSkills().size());
    }

    @Test
    public void updateProfileSkills_removingSkill() {
        String email = "Dave@live.co.uk";

        Profile profile = new Profile();
        profile.setId(email);
        profile.getSkills().addAll(Arrays.asList("Java", "Docker"));
        profileRepository.insert(profile);

        String newSkill = "Docker";
        UpdateProfileSkillsMessage message = new UpdateProfileSkillsMessage();
        message.setId(email);
        message.setRemove(true);
        message.setSkill(newSkill);

        boolean updated = profileService.updateProfileSkills(message);

        Profile retrievedProfile = profileRepository.findById(email).get();

        assertTrue(updated);
        assertFalse(retrievedProfile.getSkills().contains(newSkill));
        assertEquals(1, retrievedProfile.getSkills().size());
    }

    @Test
    public void profileExists_true() {
        String email = "Dave@live.co.uk";
        GenericMessage genericMessage = new GenericMessage();
        genericMessage.setId(email);

        Profile profile = new Profile();
        profile.setId(email);
        profile.getSkills().addAll(Arrays.asList("Java", "Docker"));
        profileRepository.insert(profile);

        boolean result = profileService.profileExists(genericMessage);

        assertTrue(result);
    }

    @Test
    public void profileExists_false() {
        String email = "Dave@live.co.uk";
        GenericMessage genericMessage = new GenericMessage();
        genericMessage.setId(email);

        boolean result = profileService.profileExists(genericMessage);

        assertFalse(result);
    }
}
