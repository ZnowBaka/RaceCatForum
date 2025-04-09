package com.example.racecatforum.Service;

import com.example.racecatforum.Entity.Cat;
import com.example.racecatforum.Entity.Profile;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserDoesNotExistsException;
import com.example.racecatforum.Framework.CatRepo;
import com.example.racecatforum.Framework.ProfileRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final ProfileRepo profileRepo;
    private final CatRepo catRepo;

    public ProfileService(ProfileRepo profileRepo, CatRepo catRepo) {
        this.profileRepo = profileRepo;
        this.catRepo = catRepo;
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

    public boolean addCatToProfile(Profile profile, Cat cat){
        if(catRepo.createNewCat(profile, cat)){
            return true;
        }
        return false;
    }

    public List<Cat> readAllCatsFromProfile(Profile profile){
        if(catRepo.getCatsFromProfile(profile) != null){
            return catRepo.getCatsFromProfile(profile);
        }
        return null;
    }



}
