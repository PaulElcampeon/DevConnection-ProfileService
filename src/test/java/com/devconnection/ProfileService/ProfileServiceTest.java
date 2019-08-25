package com.devconnection.ProfileService;

import com.devconnection.ProfileService.domain.Profile;
import com.devconnection.ProfileService.messages.CreateProfileMessage;
import com.devconnection.ProfileService.repositories.ProfileRepository;
import com.devconnection.ProfileService.services.ProfileServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;


    @Test
    public void createProfile() {
        String emailId = "Dannny@live.com";
        CreateProfileMessage createProfileMessage = new CreateProfileMessage(emailId);

        profileService.createProfile(createProfileMessage);

        verify(profileRepository, times(1)).insert(Mockito.any((Profile.class)));
    }

    @Test
    public void getProfile() {
        String emailId = "Dannny@live.com";

        when(profileRepository.findById(emailId)).thenReturn(Optional.of(new Profile(emailId)));

        profileService.getProfile(emailId);

        verify(profileRepository, times(1)).findById(emailId);
    }

    @Test(expected = NoSuchElementException.class)
    public void getProfile_NoSuchElement() {
        String emailId = "Dannny@live.com";

        profileService.getProfile(emailId);
    }

    @Test
    public void removeProfile() {
        String emailId = "Dannny@live.com";

        profileService.removeProfile(emailId);

        verify(profileRepository, times(1)).deleteById(emailId);
    }

}
