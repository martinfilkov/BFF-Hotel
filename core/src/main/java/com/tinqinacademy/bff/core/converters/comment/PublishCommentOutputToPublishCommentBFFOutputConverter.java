package com.tinqinacademy.bff.core.converters.comment;

import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFOutput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublishCommentOutputToPublishCommentBFFOutputConverter implements Converter<PublishCommentOutput, PublishCommentBFFOutput> {
    @Override
    public PublishCommentBFFOutput convert(PublishCommentOutput input) {
        log.info("Start converting from PublishCommentOutput to PublishCommentBFFOutput with input: {}", input);

        PublishCommentBFFOutput output = PublishCommentBFFOutput.builder()
                .id(input.getId())
                .build();

        log.info("End converting from PublishCommentOutput to PublishCommentBFFOutput with output: {}", output);
        return output;
    }
}
