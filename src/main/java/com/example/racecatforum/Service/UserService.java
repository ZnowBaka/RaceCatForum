package com.example.racecatforum.Service;

import com.example.racecatforum.Entity.IncorrectPasswordException;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import com.example.racecatforum.Framework.UserRepo;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.util.Objects;

public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public boolean registerUser(User user) {
        try {
            if (doesUserNameExist(user)) {
                //hashPassword(user); // for future hashing in separate method
                userRepo.registerUser(user);
                return true;
            }

        } catch (UserAlreadyExitsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // If the userName (which is Unique) already is in the database, the returned user from getUser() would NOT be null, therefore a user already exist.
    // If the return value is null, then there was no user with that username in the database, therefore if result is == to null, we return true.
    public boolean doesUserNameExist(User user) throws UserAlreadyExitsException {
        if (userRepo.getUserByUsername(user.getUserName()) == null) {
            return true;
        } else {
            throw new UserAlreadyExitsException("User with that username already exits");
        }
    }


    public void hashPassword(User user) {
        String hashed = BCrypt.hashpw(user.getUserPass(), BCrypt.gensalt());
        user.setUserPass(hashed);
    }


    public User loginUser(String username, String password) throws IncorrectPasswordException {
        User user = userRepo.getUserByUsername(username);
        if ((user != null) && Objects.equals(password, userRepo.getUserById(user.getUserId()).getUserPass())) {
            return user;
        } else {
            throw new IncorrectPasswordException("Incorrect password");
        }
    }


}
