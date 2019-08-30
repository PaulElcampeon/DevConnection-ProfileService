package com.devconnection.ProfileService.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.CreateProfile;
import com.devconnection.ProfileService.messages.GenericMessage;
import com.devconnection.ProfileService.repositories.ProfileRepository;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;


    private String email = "Dave@live.co.uk";

    private String username = "Dave123";

    @Test
    public void createProfile() {
        CreateProfile createProfile = new CreateProfile(email, username);

        profileService.createProfile(createProfile);

        verify(profileRepository, times(1)).insert(Mockito.any((Profile.class)));
    }

    @Test
    public void getProfile() {
        when(profileRepository.findById(email)).thenReturn(Optional.of(new Profile(email, username)));

        profileService.getProfile(new GenericMessage(email));

        verify(profileRepository, times(1)).findById(email);
    }

    @Test(expected = NoSuchElementException.class)
    public void getProfile_NoSuchElement() {
        String emailId = "Dannny@live.com";

        profileService.getProfile(new GenericMessage(emailId));
    }
}
