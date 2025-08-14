package com.example.saml_client_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fake-sp")
public class SamlAcsController {

    @PostMapping("/acs")
    public String handleSamlResponse() {
        // This endpoint will be handled by Spring Security's SAML2 filter
        // We just need to redirect to the standard SAML2 processing endpoint
        return "redirect:/login/saml2/sso/cas";
    }
}
