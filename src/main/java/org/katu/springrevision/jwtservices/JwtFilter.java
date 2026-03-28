package org.katu.springrevision.jwtservices;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.katu.springrevision.entity.UserDetailsServiceImp;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtFilter implements Filter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImp userDetailsServiceImp;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsServiceImp userDetailsServiceImp) {
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImp = userDetailsServiceImp;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String authorizationHeader = req.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String userName = jwtUtil.extractUsername(token);
                UserDetails userDetails = userDetailsServiceImp.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);

            }
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
