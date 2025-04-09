package com.example.racecatforum.Framework;

import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import com.example.racecatforum.Entity.UserDoesNotExistsException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        String sql = "select * from users where user_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), id);
    }


    /**
     * Code works by making a query that maps the result to a User object.
     * First it makes a new Object[], containing the "user_name" value as a query parameter
     * After the query goes through it then uses a "lambda function" to map the ResultSet (rs) to a User object.
     */
    public User getSingleUserByUsername(User user) throws UserDoesNotExistsException {
        try {
            String sql = "SELECT * FROM users WHERE user_name = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{user.getUserName()}, (rs, rowNum) -> {

                User user1 = new User();
                user1.setUserId(rs.getInt("user_id"));
                user1.setUserName(rs.getString("user_name"));
                user1.setUserPass(rs.getString("user_pass"));
                user1.setUserEmail(rs.getString("user_email"));
                System.out.println("User " + user1.getUserName() + " is in the db with id:" + user1.getUserId());
                // result (User jack is in the db with id:1) it just works
                // result User bob is in the db with id:4
                return user1;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean doesUserNameExist(String username) throws UserDoesNotExistsException {
        String sql = "select * from users where user_name = ?";
        if (jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class), username).size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * For security, in order to prevent a password from being intercepted during user creation, the password being sent to the database needs to already be encrypted.
     * In order to know if our query when through successfully, we can use "JdbcTemplate.update();", which returns the amount of rows affected by our query -
     * - it works similarly to the PreparedStatements "PreparedStatement.executeUpdate();"
     */
    public boolean createNewUser(User user) throws UserAlreadyExitsException {
        String sql = "INSERT INTO users (user_name, user_pass, user_email) VALUES (?, ?, ?)";
        int affectedRows = jdbcTemplate.update(sql, user.getUserName(), user.getUserPass(), user.getUserEmail());

        // AffectedRows should be 1 if the query was successful
        System.out.println(affectedRows);
        return affectedRows == 1;
    }

    public boolean updateUserById(User user) throws UserDoesNotExistsException {
        String sql = "UPDATE users SET user_name = ?, user_pass = ?, user_email = ? WHERE user_id = ?";
        int affectedRows = jdbcTemplate.update(sql, user.getUserName(), user.getUserPass(), user.getUserEmail(), user.getUserId());
        return affectedRows == 1;
    }

    public boolean deleteUserById(int id) throws UserDoesNotExistsException {
        String sql = "DELETE FROM users WHERE user_id = ?";
        int affectedRows = jdbcTemplate.update(sql, id);
        return affectedRows == 1;
    }


}
