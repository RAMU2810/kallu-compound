package com.kallucompound.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kallucompound.entity.Admin;

@RestController
public class AdminController {

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public AdminController(
            AuthenticationManager authenticationManager) {

        this.authenticationManager =
                authenticationManager;
    }

    // =====================================
    // ADMIN LOGIN
    // =====================================

    @PostMapping("/api/admin/login")
    public String login(
            @RequestBody Admin admin,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    admin.getUsername(),
                                    admin.getPassword()
                            )
                    );

            SecurityContext context =
                    SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);

            securityContextRepository.saveContext(
                    context,
                    request,
                    response
            );

            return "Login successful";

        } catch (Exception e) {

            return "Invalid username or password";
        }
    }

    // =====================================
    // CHECK ADMIN LOGIN
    // =====================================

    @GetMapping("/api/admin/check")
    public String checkLogin(
            Authentication authentication) {

        if (authentication != null
                && authentication.isAuthenticated()) {

            return "Admin authenticated";
        }

        return "Not authenticated";
    }
}