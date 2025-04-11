package com.example.racecatforum.Framework;


import com.example.racecatforum.Entity.IncorrectPasswordException;
import com.example.racecatforum.Entity.UserAlreadyExitsException;
import org.springframework.ui.Model;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UserAlreadyExitsException.class)
    public String handleUserAlreadyExists(UserAlreadyExitsException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "registerNewProfile"; // returnér brugeren til registreringssiden
    }


    @ExceptionHandler(IncorrectPasswordException.class)
    public String handleIncorrectPassword(IncorrectPasswordException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "login"; // returnér brugeren til login-siden
    }










    // Generic ExceptionHandler (Should be last, since this is like the pure Exception)
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, Model model) {
        model.addAttribute("error", "Der opstod en ukendt fejl. Prøv igen senere.");
        return "errorPage";
    }


}
