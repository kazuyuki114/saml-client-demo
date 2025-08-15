package com.example.saml_client_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LogoutController {

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Invalidate the session
        request.getSession().invalidate();
        
        // Redirect to CAS logout with service parameter to return to our home page
        String casLogoutUrl = "https://localhost:8080/cas/logout?service=http://localhost:8081/";
        response.sendRedirect(casLogoutUrl);
    }

    @GetMapping("/logout")
    public void logoutGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Invalidate the session
        request.getSession().invalidate();
        
        // Redirect to CAS logout with service parameter to return to our home page
        String casLogoutUrl = "https://localhost:8080/cas/logout?service=http://localhost:8081/";
        response.sendRedirect(casLogoutUrl);
    }
}
