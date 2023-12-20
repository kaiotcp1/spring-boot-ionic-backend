package com.kaio.apivendas.security;

import com.kaio.apivendas.services.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserDetailsServiceImpl userDetailService;
    private JWTUtil jwtUtil;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailsService;
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer")) {
            UsernamePasswordAuthenticationToken auth = getAuthentication(request, header.substring(7));
            if(auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            chain.doFilter(request, response);
        }

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
        if(jwtUtil.tokenValido(token)) {
            String username = jwtUtil.getUsername(token);
            UserDetails user = userDetailService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        }
        return null;


    }
}
