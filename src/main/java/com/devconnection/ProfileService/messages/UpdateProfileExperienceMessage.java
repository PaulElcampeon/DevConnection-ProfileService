package com.devconnection.ProfileService.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileExperienceMessage {

    private String id;
    private int yearsExperience;
}
