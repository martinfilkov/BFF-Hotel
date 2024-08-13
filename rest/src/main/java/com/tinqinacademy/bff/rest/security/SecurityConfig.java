package com.tinqinacademy.bff.rest.security;

import com.tinqinacademy.authentication.persistence.models.RoleType;
import com.tinqinacademy.comment.api.operations.base.CommentMappings;
import com.tinqinacademy.hotel.api.operations.base.HotelMappings;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                                //Permitted
                                //Swagger
                                .requestMatchers("/v2/api-docs",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui.html",
                                        "/webjars/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**").permitAll()

                                //Get
                                .requestMatchers(
                                        HttpMethod.GET,
                                        HotelMappings.GET_IDS,
                                        HotelMappings.GET_ROOM,
                                        CommentMappings.GET_COMMENTS).permitAll()

                                //Admin
                                //Post
                                .requestMatchers(
                                        HttpMethod.POST,
                                        HotelMappings.REGISTER_VISITOR,
                                        HotelMappings.CREATE_ROOM
                                ).hasRole(RoleType.ADMIN.toString())

                                //Get
                                .requestMatchers(
                                        HttpMethod.GET,
                                        HotelMappings.INFO_REGISTRY
                                ).hasRole(RoleType.ADMIN.toString())

                                //Put
                                .requestMatchers(
                                        HttpMethod.PUT,
                                        HotelMappings.UPDATE_ROOM
                                ).hasRole(RoleType.ADMIN.toString())

                                //Patch
                                .requestMatchers(
                                        HttpMethod.PATCH,
                                        HotelMappings.PARTIAL_UPDATE_ROOM,
                                        CommentMappings.ADMIN_UPDATE_COMMENT
                                ).hasRole(RoleType.ADMIN.toString())

                                //Delete
                                .requestMatchers(
                                        HttpMethod.DELETE,
                                        HotelMappings.DELETE_ROOM,
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
