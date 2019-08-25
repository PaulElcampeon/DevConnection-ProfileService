package com.devconnection.ProfileService.messages;

import lombok.Data;

@Data
public class UpdateProfileDescriptionMessage {

    private String id;
    private String description;

}
