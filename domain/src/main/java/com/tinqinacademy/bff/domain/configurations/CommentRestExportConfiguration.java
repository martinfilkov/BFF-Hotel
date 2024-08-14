package com.tinqinacademy.bff.domain.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.restexport.CommentRestClient;
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
public class CommentRestExportConfiguration {
    private final ObjectMapper objectMapper;

    @Value(value = "${comment.service.url}")
    private String commentUrl;

    @Bean(name = "CommentRestClient")
    public CommentRestClient commentRestClient() {
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .client(new OkHttpClient())
                .target(CommentRestClient.class, commentUrl);
    }
}
