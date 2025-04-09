package com.example.racecatforum.Service;

import com.example.racecatforum.Entity.Cat;
import com.example.racecatforum.Entity.Profile;
import com.example.racecatforum.Framework.CatRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatService {
    private final CatRepo catRepo;
    private List<Cat> cats = new ArrayList<>();
    public CatService(CatRepo catRepo) {
        this.catRepo = catRepo;
    }

    public List<Cat> getAllCats() {
        cats = catRepo.getAllCats();
        return cats;
    }

    public List<Cat> viewAllCats() {
        try{
            return cats;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public ArrayList<Cat> getAllMyCats(Profile profile) {
        return catRepo.getMyCats(profile);
    }


















}
