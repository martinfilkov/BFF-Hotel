package com.tinqinacademy.bff.domain.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import com.tinqinacademy.hotel.restexport.HotelRestClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestExportConfiguration {
    @Value(value = "${hotel.service.url}")
    private String hotelUrl;

    @Value(value = "${comment.service.url}")
    private String commentUrl;

    @Bean(name = "CommentRestClient")
    public CommentRestClient commentRestClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(CommentRestClient.class, commentUrl);
    }

    @Bean(name = "HotelRestClient")
    public HotelRestClient hotelRestClient() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .target(HotelRestClient.class, hotelUrl);
    }
}
