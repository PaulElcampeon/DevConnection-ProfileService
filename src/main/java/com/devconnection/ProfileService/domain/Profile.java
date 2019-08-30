package com.devconnection.ProfileService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "PROFILES")
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    private String id;
    private String username;
    private String description;
    private String imageUrl;
    private int yearsExperience;
    private List<String> skills = new ArrayList<>();
    private List<String> currentProjects = new ArrayList<>();

    public Profile(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
