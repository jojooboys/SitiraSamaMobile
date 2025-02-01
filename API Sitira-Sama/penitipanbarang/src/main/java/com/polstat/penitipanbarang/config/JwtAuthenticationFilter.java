package com.polstat.penitipanbarang.config;

import com.polstat.penitipanbarang.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger LOGGER = Logger.getLogger(JwtAuthenticationFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        LOGGER.info("Authorization Header: " + token);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            LOGGER.info("Extracted Token: " + token);

            try {
                String username = jwtUtil.extractUsername(token);
                LOGGER.info("Extracted Username: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    if (jwtUtil.validateToken(token, username)) {
                        LOGGER.info("Token is valid");
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                username, null, new ArrayList<>()
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        LOGGER.warning("Invalid token");
                    }
                } else {
                    LOGGER.warning("Username is null or already authenticated");
                }
            } catch (Exception e) {
                LOGGER.severe("Token validation failed: " + e.getMessage());
            }
        } else {
            LOGGER.warning("No Authorization header or invalid format");
        }

        chain.doFilter(request, response);
    }

}
