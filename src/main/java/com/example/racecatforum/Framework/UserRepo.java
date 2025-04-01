package com.example.racecatforum.Framework;

import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import com.example.racecatforum.Entity.UserDoesNotExistsException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserRepo {
    private JdbcTemplate jdbcTemplate;

    public UserRepo(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<User> getAllUsers() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    public User getUserById(int id) {
        String sql = "select * from users where id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }


    public User getUserByUsername(User user) throws UserDoesNotExistsException {
        String sql = "select * from users where user_name = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), user.getUserName());
    }

    public boolean doesUserNameExist(String username) throws UserDoesNotExistsException {
        String sql = "select * from users where user_name = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username) == null;
    }


    /**
     * For security, in order to prevent a password from being intercepted during user creation, the password being sent to the database needs to already be encrypted.
     * In order to know if our query when through successfully, we can use "JdbcTemplate.update();", which returns the amount of rows affected by our query -
     * - it works similarly to the PreparedStatements "PreparedStatement.executeUpdate();"
     */
    public boolean createNewUser(User user) throws UserAlreadyExitsException {
        String sql = "INSERT INTO users (user_name, user_pass, user_email) VALUES (?, ?, ?)";
        int affectedRows = jdbcTemplate.update(sql, user.getUserName(), user.getUserPass(), user.getUserPass());

        // AffectedRows should be 1 if the query was successful
        return affectedRows == 1;
    }


}
