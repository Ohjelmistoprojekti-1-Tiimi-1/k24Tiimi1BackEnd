package com.tiimi1.petshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    
    @GetMapping({ "/", "/login" })
    public String login() {
        return "login";
    }

    @GetMapping("/admin/home")
    public String home() {
        return "home";
    }
}
