package com.example.racecatforum.Controller;


import com.example.racecatforum.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {

    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }
}
