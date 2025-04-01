package com.example.racecatforum.Controller;

import com.example.racecatforum.Entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterUserController {
    @PostMapping
    public User registerUser(User user) {
        User newUser = new User();
        return newUser;
    }
}
