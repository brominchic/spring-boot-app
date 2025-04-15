package org.example.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;


@Controller

public class UiController {
    @GetMapping("/")
    public String getIndex(Model model, Authentication auth) {
        model.addAttribute("name",
                auth instanceof OAuth2AuthenticationToken oauth && oauth.getPrincipal() instanceof OidcUser oidc
                        ? oidc.getPreferredUsername()
                        : "");
        model.addAttribute("isAuthenticated",
                auth != null && auth.isAuthenticated());
        model.addAttribute("isNice",
                auth != null && auth.getAuthorities().stream().anyMatch(authority -> {
                    return Objects.equals("ADMIN", authority.getAuthority());
                }));

        return "index.html";
    }

    @GetMapping("/admin")
    public String getNice(Model model, Authentication auth) {
        return "nice.html";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception {
        String idToken = null;
        if (authentication != null && authentication.getPrincipal() instanceof OidcUser oidcUser) {
            idToken = oidcUser.getIdToken().getTokenValue();
        }
        String logoutUrl = "localhost:8080/realms/brom-realm/protocol/openid-connect/logout";
        if (idToken != null) {
            logoutUrl += "?id_token_hint=" + idToken;
        }
        response.sendRedirect(logoutUrl);
    }
}