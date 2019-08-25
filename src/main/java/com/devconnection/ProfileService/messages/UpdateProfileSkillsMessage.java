package com.devconnection.ProfileService.messages;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateProfileSkillsMessage {

    private String id;
    private List<String> skills = new ArrayList<>();

}
