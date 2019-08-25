package com.devconnection.ProfileService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "PROFILES")
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    private String id;
    private String description;
    private String imageUrl;
    private int yearsExperience;
    private List<String> skills = new ArrayList<>();
    private List<String> currentProjects = new ArrayList<>();

    public Profile(String id) {
        this.id = id;
    }
}
