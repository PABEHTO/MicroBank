package com.bank.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicAuthorizationController {
    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String isLoginFailed, Model model) {
        if (isLoginFailed != null) {
            model.addAttribute("isLoginFailed", true);
        }
        return "public/authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "public/authorization/registration-page";
    }
}
