package com.example.racecatforum.Framework;

import com.example.racecatforum.Entity.Profile;
import com.example.racecatforum.Entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfileRepo {
    private JdbcTemplate jdbcTemplate;

    public ProfileRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Profile> getAllProfiles() {
        String sql = "SELECT * FROM profiles";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Profile.class));
    }

    public Profile getProfileById(User user) {
        try {
            String sql = "SELECT * FROM profiles WHERE profile_userfk = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{user.getUserId()}, (rs, rowNum) -> {

                Profile profile1 = new Profile();
                profile1.setProfileId(rs.getInt("profile_id"));
                profile1.setProfileName(rs.getString("profile_name"));
                profile1.setProfileImage(rs.getString("profile_image"));
                profile1.setProfileDescription(rs.getString("profile_description"));
                return profile1;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Profile getProfileById(int id) {
        try {
            String sql = "SELECT * FROM profiles WHERE profile_id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {

                Profile profile1 = new Profile();
                profile1.setProfileId(rs.getInt("profile_id"));
                profile1.setProfileName(rs.getString("profile_name"));
                profile1.setProfileImage(rs.getString("profile_image"));
                profile1.setProfileDescription(rs.getString("profile_description"));
                return profile1;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int addProfile(User user, Profile profile) {
        String sql = "INSERT INTO profiles(profile_userfk, profile_name, profile_image, profile_description) VALUES (?, ?, ?, ?)";
        int affectedRows = jdbcTemplate.update(sql, user.getUserId(), profile.getProfileName(), profile.getProfileImage(), profile.getProfileDescription());
        return affectedRows;
    }

    public int updateProfileName(Profile profile) {
        String sql = "UPDATE profiles SET profile_name = ? WHERE profile_id = ?";
        int affectedRows = jdbcTemplate.update(sql, profile.getProfileName(), profile.getProfileId());
        return affectedRows;
    }

    public int updateProfileImage(Profile profile) {
        String sql = "UPDATE profiles SET profile_image = ? WHERE profile_id = ?";
        int affectedRows = jdbcTemplate.update(sql, profile.getProfileImage(), profile.getProfileId());
        return affectedRows;
    }
    public int updateProfileDescription(Profile profile) {
        String sql = "UPDATE profiles SET profile_description = ? WHERE profile_id = ?";
        int affectedRows = jdbcTemplate.update(sql, profile.getProfileDescription(), profile.getProfileId());
        return affectedRows;
    }

    public boolean updateProfile(Profile profile) {
        String sql = "UPDATE profiles SET profile_name = ?, profile_image = ?, profile_description = ?  WHERE profile_id = ?";
        int affectedRows = jdbcTemplate.update(sql,profile.getProfileName(), profile.getProfileImage(), profile.getProfileDescription(), profile.getProfileId());
        return affectedRows == 1;
    }


}
