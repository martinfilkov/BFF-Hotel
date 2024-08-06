package com.tinqinacademy.bff.domain.noconfiguration;

import com.tinqinacademy.comment.restexport.CommentRestClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "comment-service", url = "${comment.service.url}", configuration = FeignConfiguration.class)
public interface CommentServiceRestExport extends CommentRestClient {
}
