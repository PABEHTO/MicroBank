package com.bank.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PublicHomeController {
    @RequestMapping(name = "/", method = RequestMethod.GET)
    public String getHomePage() {
        return "public/home-page";
    }
}
