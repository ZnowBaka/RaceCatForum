package com.example.racecatforum.Service;

import com.example.racecatforum.Entity.IncorrectPasswordException;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import com.example.racecatforum.Entity.UserDoesNotExistsException;
import com.example.racecatforum.Framework.UserRepo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    // If the userName exist in database, the value from getUser() would NOT be null, therefore a user already exist.
    public boolean registerUser(User user) {
        try {
            if (userRepo.doesUserNameExist(user.getUserName()) == false) {
                //hashPassword(user); // for future hashing in separate method
                System.out.println("testing registerUser");
                userRepo.createNewUser(user);
                return true;
            }
        } catch (UserAlreadyExitsException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }


    public void hashPassword(User user) {
        String hashed = BCrypt.hashpw(user.getUserPass(), BCrypt.gensalt());
        user.setUserPass(hashed);
    }

    public User getUserByUsername(String username) {
        User user = new User();
        user.setUserName(username);
        user = userRepo.getSingleUserByUsername(user);
        return user;
    }


    public User loginUser(User user) throws IncorrectPasswordException {
        try {
            User user2 = userRepo.getSingleUserByUsername(user);
            if (user2 != null) {
                if (user.getUserName().equals(user2.getUserName()) && user2.getUserPass().equals(user.getUserPass())) {
                    return user2;
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new IncorrectPasswordException(e.getMessage());
        }
        return null;
    }

    public boolean updateUser(User user) throws UserDoesNotExistsException {
        try {
            return userRepo.updateUserById(user);
        } catch (Exception e) {
            throw new UserDoesNotExistsException(e.getMessage());
        }
    }

    public boolean deleteUser(User user) {
        try {
            return userRepo.deleteUserById(user.getUserId());
        } catch (Exception e) {
            throw new UserDoesNotExistsException(e.getMessage());
        }
    }


}
