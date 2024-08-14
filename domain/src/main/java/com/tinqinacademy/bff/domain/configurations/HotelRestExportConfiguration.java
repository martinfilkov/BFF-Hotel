package com.tinqinacademy.bff.domain.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.hotel.restexport.HotelRestClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HotelRestExportConfiguration {
    private final ObjectMapper objectMapper;

    @Value(value = "${hotel.service.url}")
    private String hotelUrl;

    @Bean(name = "HotelRestClient")
    public HotelRestClient hotelRestClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .client(new OkHttpClient())
                .target(com.tinqinacademy.hotel.restexport.HotelRestClient.class, hotelUrl);
    }
}
