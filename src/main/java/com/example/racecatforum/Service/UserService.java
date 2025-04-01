package com.example.racecatforum.Service;

import com.example.racecatforum.Entity.IncorrectPasswordException;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import com.example.racecatforum.Framework.UserRepo;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    // If the userName exist in database, the value from getUser() would NOT be null, therefore a user already exist.
    public boolean registerUser(User user) {
        try {
            if (userRepo.doesUserNameExist(user.getUserName()) == false)
            //hashPassword(user); // for future hashing in separate method
                System.out.println("testing registerUser");
                userRepo.createNewUser(user);
            return false;
        } catch (UserAlreadyExitsException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }




    public void hashPassword(User user) {
        String hashed = BCrypt.hashpw(user.getUserPass(), BCrypt.gensalt());
        user.setUserPass(hashed);
    }

    public User getUserByUsername(String username) {
        User user = new User();
        user.setUserName(username);
        user = userRepo.getUserByUsername(user);
        return user;
    }


    public User loginUser(User user) throws IncorrectPasswordException {
        if (userRepo.getUserByUsername(user) != null) {
            return user;
        } else {
            throw new IncorrectPasswordException("Incorrect password");
        }
    }


}
