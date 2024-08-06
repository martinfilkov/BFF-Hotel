package com.tinqinacademy.bff.domain.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommentRestExportConfiguration {
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
}
