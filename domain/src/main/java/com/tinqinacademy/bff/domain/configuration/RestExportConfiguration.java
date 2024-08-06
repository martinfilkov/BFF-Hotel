package com.tinqinacademy.bff.domain.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import com.tinqinacademy.hotel.restexport.HotelRestClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestExportConfiguration {
    @Bean(name = "CommentRestClient")
    public CommentRestClient commentRestClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(CommentRestClient.class, "${comment.service.url}");
    }

    @Bean(name = "HotelRestClient")
    public HotelRestClient hotelRestClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .contract(new SpringMvcContract())
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(HotelRestClient.class, "${hotel.service.url}");
    }
}
