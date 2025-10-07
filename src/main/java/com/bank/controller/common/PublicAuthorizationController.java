package com.bank.controller.common;

import com.bank.entity.User;
import com.bank.entity.UserRole;
import com.bank.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Set;

@Controller
public class PublicAuthorizationController {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public PublicAuthorizationController(PasswordEncoder passwordEncoder,
                                         UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "public/authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "public/authorization/registration-page";
    }

    @PostMapping("/registration")
    public String createUser(@RequestParam("username") String username,
                             @RequestParam("email") String email,
                             @RequestParam("password") String password) {
        String encodedPassword = passwordEncoder.encode(password);
        userService.saveUser(new User(
                username,
                email, encodedPassword,
                UserRole.USER));
        autoLogin(email, password);
        return "redirect:/my-page";
    }

    private void autoLogin(String username, String password) {
        Set<SimpleGrantedAuthority> roles = Collections.singleton(UserRole.USER.getAuthority());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
