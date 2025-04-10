package com.example.racecatforum.Controller;

import com.example.racecatforum.Entity.Cat;
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
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping("/")
    public String home(Model model) {
        return "redirect:/registerNewProfile";
    }


    //region Register Profile Get/Put/Post
    @GetMapping("/registerNewProfile")
    public String getNewProfile(Model model) {
        model.addAttribute("newUser", new User());
        return "/registerNewProfile";
    }

    @PostMapping("/registerNewProfile")
    public String postNewProfile(@ModelAttribute("newUser") User user, Model model) {
        if (userService.registerUser(user)) {
            try {
                userService.loginUser(user);
                session.setAttribute("user", userService.getUserByUsername(user.getUserName()));
            } catch (IncorrectPasswordException e) {
                e.printStackTrace();
            }
            return "redirect:/setupMyProfile";
        } else {
            model.addAttribute("error", "User already exists");
            return "/registerNewProfile";
        }
    }
    //endregion


    //region Setup Profile Get/Put/Post
    @GetMapping("/setupMyProfile")
    public String getSetupMyProfile(Model model) {
        model.addAttribute("newProfile", new Profile());
        return "setupMyProfile";
    }

    @PostMapping("/setupMyProfile")
    public String postSetupMyProfile(Model model, @ModelAttribute("newProfile") Profile profile) {
        System.out.println(profile.getProfileName() + " " + profile.getProfileId());
        System.out.println("sending profile");
        profileService.NewProfile((User) session.getAttribute("user"), profile);
        session.setAttribute("profile", profileService.getProfileById((User) session.getAttribute("user")));
        return "redirect:/frontPage";
    }
    //endregion


    @GetMapping("/loginPage")
    public String loginPage(Model model) {
        session.invalidate();
        session.isNew();
        model.addAttribute("user", new User());
        return "/loginPage";
    }

    @PostMapping("/loginPage")
    public String postLoginPage(@ModelAttribute("user") User user, Model model) throws IncorrectPasswordException {
        User loggedInUser = userService.loginUser(user);
        if (loggedInUser != null) {

            session.setAttribute("user", loggedInUser);
            session.setAttribute("profile", profileService.getProfileById((User) session.getAttribute("user")));
            return "redirect:/frontPage";
        } else if (loggedInUser == null) {
            model.addAttribute("error", "Incorrect username or password");
            return "/loginPage";
        }
        return "/loginPage";
    }


    @GetMapping("/frontPage")
    public String frontPage(Model model) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("cats", catService.getAllCats());

        return "/frontPage";
    }


    @GetMapping("/myProfile")
    public String getMyProfile(Model model) {
        Profile profile = (Profile) session.getAttribute("profile");

        model.addAttribute("profile", profile);
        model.addAttribute("cats", profileService.readAllCatsFromProfile(profile));

        return "/myProfile";
    }


    @GetMapping("/addCat")
    public String addCatToProfile(Model model) {
        model.addAttribute("newCat", new Cat());

        return "/addCat";
    }

    @PostMapping("/addCat")
    public String addCatToProfile(@ModelAttribute("newCat") Cat cat, Model model) {
        Profile profile = (Profile) session.getAttribute("profile");
        profileService.addCatToProfile(profile, cat);
        return "redirect:/myProfile";
    }

    @GetMapping("/editCat/{id}")
    public String editCat(Model model, @PathVariable int id) {
        model.addAttribute("cat",catService.getCatById((int)id));

        return "/editCat";
    }

    @PostMapping("/updateCat/{id}")
    public String updateCat(@ModelAttribute Cat cat) {
        if (catService.updateCat(cat)){
            return "redirect:/myProfile";
        } else {
            return "redirect:/editCat/{id}";
        }

    }

    @GetMapping("/deleteCat/{id}")
    public String deleteCat(@ModelAttribute Cat cat, @PathVariable int id) {
        catService.deleteCat(id);

        return "redirect:/myProfile";
    }



}
/*
  if (catService.viewAllCats().isEmpty()) {
        catService.getAllCats();
            model.addAttribute("cats", catService.viewAllCats());
        } else {
        model.addAttribute("cats", catService.viewAllCats());
        }*/