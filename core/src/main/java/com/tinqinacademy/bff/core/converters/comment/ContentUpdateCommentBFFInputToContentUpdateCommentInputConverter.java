package com.tinqinacademy.bff.core.converters.comment;

import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentUpdateCommentBFFInputToContentUpdateCommentInputConverter implements Converter<ContentUpdateCommentBFFInput, ContentUpdateCommentInput> {
    @Override
    public ContentUpdateCommentInput convert(ContentUpdateCommentBFFInput input) {
        log.info("Start converting from ContentUpdateCommentBFFInput to ContentUpdateCommentInput with input: {}", input);

        ContentUpdateCommentInput output = ContentUpdateCommentInput.builder()
                .content(input.getContent())
                .userId(input.getUserId())
                .build();

        log.info("End converting from ContentUpdateCommentBFFInput to ContentUpdateCommentInput with output: {}", output);
        return output;
    }
}
