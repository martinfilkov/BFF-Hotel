package com.tinqinacademy.bff.rest.security;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.api.operations.operations.validate.ValidateUserInput;
import com.tinqinacademy.authentication.restexport.AuthenticationRestClient;
import com.tinqinacademy.bff.persistence.JWTContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationRestClient authenticationRestClient;
    private final ObjectMapper objectMapper;
    private final JWTContext jwtContext;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
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
                ValidateUserInput validateInput = ValidateUserInput.builder()
                        .token(jwt)
                        .build();

                Boolean isTokenValid = authenticationRestClient.validateToken(validateInput).getValidity();
                if (isTokenValid) {
                    Map<String, String> payloadMap = getPayload(jwt);

                    CustomUser user = CustomUser.builder()
                            .username(payloadMap.get("user_id"))
                            .role(payloadMap.get("role"))
                            .build();

                    CustomAuthToken authToken = new CustomAuthToken(user);
                    jwtContext.setUserId(user.getUsername());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private Map<String, String> getPayload(String jwt) throws JsonProcessingException {
        String[] parts = jwt.split("\\.");

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

        return objectMapper.readValue(payload, Map.class);
    }
}
