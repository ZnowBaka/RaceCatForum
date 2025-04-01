package com.example.racecatforum.Controller;

import com.example.racecatforum.Service.CatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontPageController {
    @Autowired
    private final CatService catService;
    public FrontPageController(CatService catService) {
        this.catService = catService;
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
    public String frontPage1() {
        return "registerNewProfile";
    }

    @GetMapping("/frontPage")
    public String frontPage(Model model) {
        if(catService.viewAllCats().isEmpty()){
            catService.getAllCats();
            model.addAttribute("cats", catService.viewAllCats());
        } else {
            model.addAttribute("cats", catService.viewAllCats());
        }
        return "frontPage";
    }
}
