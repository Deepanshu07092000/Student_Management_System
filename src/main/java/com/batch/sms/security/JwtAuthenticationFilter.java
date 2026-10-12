package com.batch.sms.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. Read the Authorization header
        String authHeader = request.getHeader("Authorization");

        // 2. No Bearer token -> continue without authenticating
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the JWT
        String token = authHeader.substring(7);

        try {
            String username = jwtService.extractUsername(token);

            // CHANGED: removed "&& SecurityContextHolder.getContext().getAuthentication() == null"
            // A valid Bearer token must always decide who the caller is,
            // even if a session (e.g. Google login) already set an authentication.
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(token, userDetails)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    // CHANGED: System.out replaced with proper debug logging
                    log.debug("JWT authenticated user={} authorities={}", username, userDetails.getAuthorities());
                } else {
                    log.debug("JWT rejected: token not valid for user={}", username);
                }
            }
        } catch (RuntimeException ex) {
            // CHANGED: log the reason instead of swallowing it silently.
            // Request continues unauthenticated; security rules will reject protected APIs.
            SecurityContextHolder.clearContext();
            log.warn("JWT processing failed: {}", ex.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
