package com.example.racecatforum.Controller;

import com.example.racecatforum.Entity.IncorrectPasswordException;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import com.example.racecatforum.Framework.UserRepo;
import com.example.racecatforum.Service.CatService;
import com.example.racecatforum.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontPageController {
    private final CatService catService;
    private final UserService userService;
    private final HttpSession session;

    public FrontPageController(CatService catService, UserRepo userRepo, UserService userService, HttpSession session) {
        this.catService = catService;
        this.userService = userService;
        this.session = session;
    }

    /*
        @GetMapping("/")
        public String welcome(HttpSession session, Model model) {
            if (session.getAttribute("sessionUser") == null) {
                return "redirect:/registerNewProfile";
            } else {
                model.addAttribute("sessionUser", session.getAttribute("sessionUser"));
                return "/frontPage";
            }
        }
        */

    @GetMapping("/")
    public String home(Model model) {
        //model.addAttribute("cats", catService.getAllCats());
        return "redirect:/registerNewProfile";
    }


    @GetMapping("/registerNewProfile")
    public String getNewProfile(Model model) {
        model.addAttribute("newUser", new User());
        return "/registerNewProfile";
    }

    @PostMapping("/registerNewProfile")
    public String postNewProfile(@ModelAttribute("newUser") User user, Model model) {
        if (!userService.registerUser(user)) {
            return "redirect:/loginPage";
        } else {
            model.addAttribute("error", "User already exists");
            return "/registerNewProfile";
        }
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "/loginPage";
    }

    @PostMapping("/loginPage")
    public String postLoginPage(@ModelAttribute("user") User user, Model model) throws IncorrectPasswordException {
        User loggedInUser = userService.loginUser(user);
        if (loggedInUser != null) {
            session.setAttribute("currentUser", loggedInUser);
            return "redirect:/frontPage";
        } else if (loggedInUser == null) {
            model.addAttribute("error", "Incorrect username or password");
            return "/loginPage";
        }
        return "/loginPage";
    }


    @GetMapping("/frontPage")
    public String frontPage(Model model) {
        User user = (User) session.getAttribute("currentUser");
        model.addAttribute("user", user);
        return "/frontPage";
    }
}
/*
  if (catService.viewAllCats().isEmpty()) {
        catService.getAllCats();
            model.addAttribute("cats", catService.viewAllCats());
        } else {
        model.addAttribute("cats", catService.viewAllCats());
        }*/