package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.authentication.persistence.models.RoleType;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.hotel.api.operations.base.HotelMappings;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/v2/api-docs",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**").permitAll()
                                .requestMatchers(
                                        HotelMappings.GET_IDS,
                                        HotelMappings.GET_ROOM,
                                        CommentMappings.GET_COMMENTS).permitAll()
                                .requestMatchers(
                                        HotelMappings.REGISTER_VISITOR,
                                        HotelMappings.INFO_REGISTRY,
                                        HotelMappings.CREATE_ROOM,
                                        HotelMappings.UPDATE_ROOM,
                                        HotelMappings.PARTIAL_UPDATE_ROOM,
                                        HotelMappings.DELETE_ROOM,
                                        CommentMappings.ADMIN_UPDATE_COMMENT,
                                        CommentMappings.DELETE_COMMENT).hasRole(RoleType.ADMIN.toString())
                                .anyRequest().authenticated()
                )
                //Това означава, че дори да е бил authenticate-нат user-а, ние пак ще го проверим
                //Няма да пазим някакъв негов state от предишно влизане
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
