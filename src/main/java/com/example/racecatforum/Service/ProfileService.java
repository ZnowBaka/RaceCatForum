package com.example.racecatforum.Service;

import com.example.racecatforum.Entity.Profile;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserDoesNotExistsException;
import com.example.racecatforum.Framework.ProfileRepo;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final ProfileRepo profileRepo;
    public ProfileService(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }
    public boolean profileExists(User user){
        if(profileRepo.getProfileById(user) != null){
            return true;
        }
        return false;
    }
    public Profile getProfileById(User user){
        return profileRepo.getProfileById(user);
    }
    public Profile NewProfile(User user, Profile profile){
        if(profileRepo.addProfile(user, profile) == 1){
            return profile;
        }
        return null;
    }
}
