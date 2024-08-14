package com.tinqinacademy.bff.core.converters.comment;

import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PublishCommentBFFInputToPublishCommentInputConverter implements Converter<PublishCommentBFFInput, PublishCommentInput> {
    @Override
    public PublishCommentInput convert(PublishCommentBFFInput input) {
        log.info("Start converting from PublishCommentBFFInput to PublishCommentInput with input: {}", input);

        PublishCommentInput output = PublishCommentInput.builder()
                .content(input.getContent())
                .userId(input.getUserId())
                .build();

        log.info("End converting from PublishCommentBFFInput to PublishCommentInput with output: {}", output);
        return output;
    }
}
