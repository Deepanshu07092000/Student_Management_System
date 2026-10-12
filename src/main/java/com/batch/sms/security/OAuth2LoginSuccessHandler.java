package com.batch.sms.security;

import com.batch.sms.entity.User;
import com.batch.sms.service.GoogleOAuth2UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final GoogleOAuth2UserService googleOAuth2UserService;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // Get the authenticated Google user's details
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        Boolean emailVerified = oauth2User.getAttribute("email_verified");

        // Accept only Google accounts with verified email addresses.
        if (email == null || email.isBlank() || !Boolean.TRUE.equals(emailVerified)) {

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Google email is missing or not verified");
            return;
        }

        // Find the existing user or register a new Google user.
        User user = googleOAuth2UserService.findOrCreateUser(email);

        // Load the user's database-backed authorities.
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        // Generate the application's own JWT.
        String token = jwtService.generateToken(userDetails);

        // Return the JWT in the response.
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> result = Map.of(
                "accessToken", token,
                "tokenType", "Bearer",
                "expiresIn", 900
        );

        objectMapper.writeValue(response.getWriter(), result);
    }
}
