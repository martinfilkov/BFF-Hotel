package com.tinqinacademy.bff.rest.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.api.operations.operations.validate.ValidateUserInput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestClient;
import com.tinqinacademy.bff.api.operations.exceptions.IllegalTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AuthenticationRestClient authenticationRestClient;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                String[] parts = jwt.split("\\.");

                if (parts.length != 3) {
                    throw new IllegalTokenException("Invalid JWT token");
                }

                String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

                Map<String, String> payloadMap = objectMapper.readValue(payload, Map.class);

                CustomUser user = CustomUser.builder()
                        .username(payloadMap.get("user_id"))
                        .role(payloadMap.get("role"))
                        .build();

                ValidateUserInput validateInput = ValidateUserInput.builder()
                        .username(user.getUsername())
                        .token(jwt)
                        .build();

                Boolean isTokenValid = authenticationRestClient.validateUser(validateInput).getValidity();
                if (isTokenValid) {

                    CustomAuthToken authToken = new CustomAuthToken(user);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
