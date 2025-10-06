package com.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SecurityConfig {

    public SecurityConfig() {

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(d -> d.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/")
                        .permitAll()
                        .requestMatchers("/resources/**", "/favicon.ico", "/.well-known/**")
                        .permitAll())
                .formLogin(fl -> fl
                        .loginPage("/login")
                        .permitAll()
                        .usernameParameter("login")
                        .defaultSuccessUrl("/account")
                        .failureUrl("/login?error")
                        .loginProcessingUrl("/login"))
                .logout(l -> l
                        .logoutUrl("/login")
                        .logoutSuccessUrl("/logout?logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .permitAll())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/account/**").authenticated()
                        .anyRequest().permitAll())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
