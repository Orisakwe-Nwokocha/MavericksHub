package com.maverickstube.maverickshub.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

import static com.maverickstube.maverickshub.security.utils.SecurityUtils.JWT_PREFIX;
import static com.maverickstube.maverickshub.security.utils.SecurityUtils.PUBLIC_ENDPOINTS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String requestPath = request.getServletPath();
        boolean isRequestPathPublic = PUBLIC_ENDPOINTS.contains(requestPath);
        if (isRequestPathPublic) filterChain.doFilter(request, response);
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        System.out.println("header: "+authorizationHeader);
        if (authorizationHeader != null) {
            String token = authorizationHeader.substring(JWT_PREFIX.length()).strip();
            System.out.println("token: "+token);
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512("secret".getBytes()))
                    .withIssuer("mavericks_hub")
                    .withClaimPresence("roles")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            List<? extends GrantedAuthority> authorities = decodedJWT
                    .getClaim("roles")
                    .asList(SimpleGrantedAuthority.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(null, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
