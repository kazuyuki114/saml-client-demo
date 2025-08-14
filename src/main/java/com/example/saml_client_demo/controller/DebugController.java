package com.example.saml_client_demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class DebugController {

    @GetMapping("/debug")
    @ResponseBody
    public String debug() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
            return "No authentication found in SecurityContext";
        }
        
        return "Authentication: " + auth + 
               "<br>Principal: " + auth.getPrincipal() + 
               "<br>Authorities: " + auth.getAuthorities() +
               "<br>Details: " + auth.getDetails() +
               "<br>Authenticated: " + auth.isAuthenticated();
    }

    @GetMapping("/saml-debug")
    @ResponseBody
    public String samlDebug(@AuthenticationPrincipal Saml2AuthenticatedPrincipal principal) {
        if (principal == null) {
            return "Not authenticated via SAML";
        }
        
        return "SAML Authenticated as: " + principal.getName() + 
               "<br>Attributes: " + principal.getAttributes() +
               "<br>Registration ID: " + principal.getRelyingPartyRegistrationId();
    }
}
