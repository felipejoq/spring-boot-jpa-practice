package com.uncodigo.springboot.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model, Principal principal,
                        RedirectAttributes flash) {

        if (principal != null) {
            flash.addFlashAttribute("info", "Usted ya inició sesión.");
            return "redirect:/";
        } else {
            model.addAttribute("titulo", "Login - Aplicación");
        }

        if (error != null) {
            model.addAttribute("titulo", "Error en el Login - Aplicación");
            model.addAttribute("error", "Credenciales incorrectas, inténtelo nuevamente.");
        }

        if (logout != null) {
            model.addAttribute("titulo", "Desconectado con éxito - Aplicación");
            model.addAttribute("success", "Sesión cerrada con éxito.");
        }


        return "login";
    }

}
