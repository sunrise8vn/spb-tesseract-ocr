package com.cg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/canvas")
    public String showCanvasPage() {
        return "canvas";
    }
}
