package com.example.racecatforum.Framework;


import com.example.racecatforum.Entity.Cat;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import com.example.racecatforum.Entity.UserDoesNotExistsException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatRepo {
    private JdbcTemplate jdbcTemplate;

    public CatRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Cat> getAllCats() {
        String sql = "select * from cats";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Cat.class));
    }


    public Cat getCatById(int catId) {
        String sql = "select * from cats where cat_id=?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Cat.class), catId);
    }


    public Cat getSingleCatByName(String catName) {
        try {
            String sql = "SELECT * FROM cats WHERE cat_name = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{catName}, (rs, rowNum) -> {

                Cat catFromDB = new Cat();
                catFromDB.setCatId(rs.getInt("cat_id"));
                catFromDB.setOwnerId(rs.getInt("cat_owner_id"));
                catFromDB.setCatName(rs.getString("cat_name"));
                catFromDB.setCatDescription(rs.getString("cat_description"));
                catFromDB.setCatGender(rs.getString("cat_gender"));
                catFromDB.setCatImage(rs.getString("cat_image"));
                catFromDB.setCatAge(rs.getInt("cat_age"));

                return catFromDB;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    public boolean createNewCat(Cat cat) {
        String sql = "INSERT INTO cats (cat_owner_id, cat_name, cat_description, cat_gender, cat_image, cat_age) VALUES (?, ?, ? , ?, ?, ?)";
        int affectedRows = jdbcTemplate.update(sql, cat.getOwnerId(), cat.getCatName(), cat.getCatDescription(), cat.getCatGender(), cat.getCatImage(), cat.getCatAge());

        System.out.println(affectedRows);
        return affectedRows == 1;
    }

    public boolean updateCatById(Cat cat) throws UserDoesNotExistsException {
        String sql = "UPDATE cats SET cat_name = ?, cat_description = ?, cat_gender = ?, cat_image = ?, cat_age = ? WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, cat.getCatName(), cat.getCatDescription(), cat.getCatGender(), cat.getCatImage(), cat.getCatAge());
        return affectedRows == 1;
    }

    public boolean deleteCatById(int id) {
        String sql = "DELETE FROM cats WHERE id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        return affectedRows == 1;
    }


}
