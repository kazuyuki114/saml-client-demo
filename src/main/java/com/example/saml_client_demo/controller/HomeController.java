package com.example.saml_client_demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
        model.addAttribute("name", principal.getName());
        model.addAttribute("attributes", principal.getAttributes());
        return "dashboard";
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal, Model model) {
        model.addAttribute("principal", principal);
        model.addAttribute("name", principal.getName());
        model.addAttribute("attributes", principal.getAttributes());
        model.addAttribute("sessionIndexes", principal.getSessionIndexes());
        return "profile";
    }


}
