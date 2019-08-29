package com.devconnection.ProfileService.controllers;

import com.devconnection.ProfileService.ProfileServiceApplication;
import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.*;
import com.devconnection.ProfileService.repositories.ProfileRepository;
import com.devconnection.ProfileService.services.ProfileService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest(classes = {ProfileServiceApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileServiceControllerTest {

    @LocalServerPort
    private int port;

    private String baseUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @Before
    public void init() {
        baseUrl = String.format("http://localhost:%d/", port);
    }

    @After
    public void tearDown() {
        profileRepository.deleteAll();
    }


    @Test
    public void createProfile() {
        String email = "Dave@live.co.uk";

        restTemplate.postForEntity(baseUrl + "create", new GenericMessage(email), String.class);

        assertTrue(profileService.profileExists(new GenericMessage(email)));
    }

    @Test
    public void getProfile() {
        String email = "Dave@live.co.uk";

        profileRepository.insert(new Profile(email));

        HttpEntity<Profile> result = restTemplate.postForEntity(baseUrl + "get", new GenericMessage(email), Profile.class);

        assertEquals(new Profile(email), result.getBody());
    }

    @Test
    public void checkProfileExists() {
        String email = "Dave@live.co.uk";

        profileRepository.insert(new Profile(email));

        HttpEntity<GenericResponse> result = restTemplate.postForEntity(baseUrl + "exists", new GenericMessage(email), GenericResponse.class);

        assertTrue(result.getBody().isSuccess());
    }

    @Test
    public void updateProjects() {
        String email = "Dave@live.co.uk";
        String projectName = "Jeanuto";
        Profile profile = new Profile(email);
        profile.getCurrentProjects().add(projectName);

        profileRepository.insert(profile);

        HttpEntity<GenericResponse> result = restTemplate.postForEntity(baseUrl + "update/projects", new UpdateProfileCurrentProjectsMessage(email, projectName, true), GenericResponse.class);

        assertTrue(result.getBody().isSuccess());
        assertEquals(0, profileRepository.findById(email).get().getCurrentProjects().size());
    }

    @Test
    public void updateDescription() {
        String email = "Dave@live.co.uk";
        Profile profile = new Profile(email);
        profile.setDescription("hello");

        profileRepository.insert(profile);

        HttpEntity<GenericResponse> result = restTemplate.postForEntity(baseUrl + "update/description", new UpdateProfileDescriptionMessage(email, "Bye"), GenericResponse.class);

        assertTrue(result.getBody().isSuccess());
        assertEquals("Bye", profileRepository.findById(email).get().getDescription());
    }

    @Test
    public void updateExperience() {
        String email = "Dave@live.co.uk";
        Profile profile = new Profile(email);
        profile.setYearsExperience(2);

        profileRepository.insert(profile);

        int newYearsExperience = 4;

        HttpEntity<GenericResponse> result = restTemplate.postForEntity(baseUrl + "update/experience", new UpdateProfileExperienceMessage(email, newYearsExperience), GenericResponse.class);

        assertTrue(result.getBody().isSuccess());
        assertEquals(newYearsExperience, profileRepository.findById(email).get().getYearsExperience());
    }

    @Test
    public void updateSkills() {
        String email = "Dave@live.co.uk";
        Profile profile = new Profile(email);
        profile.getSkills().add("Java");

        profileRepository.insert(profile);

        String skill = "React";

        HttpEntity<GenericResponse> result = restTemplate.postForEntity(baseUrl + "update/skills", new UpdateProfileSkillsMessage(email, false, skill), GenericResponse.class);

        assertTrue(result.getBody().isSuccess());
        assertTrue(profileRepository.findById(email).get().getSkills().contains(skill));
    }


}
