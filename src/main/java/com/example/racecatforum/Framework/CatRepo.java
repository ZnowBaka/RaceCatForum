package com.example.racecatforum.Framework;


import com.example.racecatforum.Entity.Cat;
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

}
