package com.devconnection.ProfileService.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileCurrentProjectsMessage {

    private String email;
    private String projectName;
    private boolean leave;
}
