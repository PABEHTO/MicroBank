package com.bank.controller.secured;

import com.bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my-page")
public class PrivateAccessController {
    private final UserService userService;

    @Autowired
    public PrivateAccessController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getMyPage(Model model) {
        model.addAttribute("myName", userService.getTheUser().getUsername());
        model.addAttribute("myAccount", userService.getTheUser().getAccount());

        return "private/personal-page";
    }

    /*
    @PostMapping("/delete")
    public String deleteMyAccount() {
        userService.deleteUser(userService.getTheUser());
        return "redirect:/login";
    }*/
}
