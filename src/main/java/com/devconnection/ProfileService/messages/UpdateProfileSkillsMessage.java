package com.devconnection.ProfileService.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileSkillsMessage {

    private String id;
    private boolean remove;
    private String skill;

}
