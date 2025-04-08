package com.example.racecatforum.Controller;

import com.example.racecatforum.Entity.IncorrectPasswordException;
import com.example.racecatforum.Entity.Profile;
import com.example.racecatforum.Entity.User;
import com.example.racecatforum.Service.CatService;
import com.example.racecatforum.Service.ProfileService;
import com.example.racecatforum.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FrontPageController {
    private final CatService catService;
    private final UserService userService;
    private final HttpSession session;
    private final ProfileService profileService;

    public FrontPageController(CatService catService, UserService userService, HttpSession session, ProfileService profileService) {
        this.catService = catService;
        this.userService = userService;
        this.session = session;
        this.profileService = profileService;
    }


    @GetMapping("/setupMyProfile")
    public String myProfileSetup(Model model) {
        model.addAttribute("newProfile", new Profile());
        return "setupMyProfile";
    }
    @PostMapping("/setupMyProfile")
    public String setupMyProfile(Model model, @ModelAttribute("newProfile") Profile profile) {
        session.setAttribute("profile", profile);
        return "/frontPage";
    }

    @GetMapping("/myProfile")
    public String myProfile(Model model) {
        Profile profile = (Profile) session.getAttribute("profile");

        model.addAttribute("profile", profile);

        return "/myProfile";
    }



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
        if (userService.registerUser(user)) {
            try{
                userService.loginUser(user);
            } catch (IncorrectPasswordException e) {
                e.printStackTrace();
            }
            return "redirect:/setupMyProfile";
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