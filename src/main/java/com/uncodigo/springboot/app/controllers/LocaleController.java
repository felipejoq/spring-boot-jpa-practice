package com.uncodigo.springboot.app.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LocaleController {

    @GetMapping("/locale")
    public String local(HttpServletRequest request){
        String ultimaUrl = request.getHeader("referer");

        return "redirect:".concat(ultimaUrl);
    }

}
