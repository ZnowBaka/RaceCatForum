package com.example.racecatforum.Entity;

import java.util.ArrayList;

public class Profile {
    private ArrayList<Cat> personalCats;
    private int profileId;
    private String profileName;
    private String profileImage;
    private String profileDescription;

    public Profile(int profileId, String profileName, String profileImage, String profileDescription) {
        this.profileId = profileId;
        this.profileName = profileName;
        this.profileImage = profileImage;
        this.profileDescription = profileDescription;
    }

    public Profile(ArrayList<Cat> personalCats, int profileId, String profileName, String profileImage, String profileDescription) {
        this.personalCats = personalCats;
        this.profileId = profileId;
        this.profileName = profileName;
        this.profileImage = profileImage;
        this.profileDescription = profileDescription;
    }

    public Profile() {
    }
    public Profile(String profileName, String profileImage, String profileDescription) {
        this.profileName = profileName;
        this.profileImage = profileImage;
        this.profileDescription = profileDescription;
    }

    public ArrayList<Cat> getPersonalCats() {
        return personalCats;
    }

    public void setPersonalCats(ArrayList<Cat> personalCats) {
        this.personalCats = personalCats;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }
}
