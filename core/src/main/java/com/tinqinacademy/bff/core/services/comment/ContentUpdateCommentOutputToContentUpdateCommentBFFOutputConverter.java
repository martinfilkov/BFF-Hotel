package com.tinqinacademy.bff.core.services.comment;

import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFOutput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ContentUpdateCommentOutputToContentUpdateCommentBFFOutputConverter implements Converter<ContentUpdateCommentOutput, ContentUpdateCommentBFFOutput> {
    @Override
    public ContentUpdateCommentBFFOutput convert(ContentUpdateCommentOutput input) {
        log.info("Start converting from ContentUpdateCommentOutput to ContentUpdateCommentBFFOutput with input: {}", input);

        ContentUpdateCommentBFFOutput output = ContentUpdateCommentBFFOutput.builder()
                .id(input.getId())
                .build();

        log.info("End converting from ContentUpdateCommentOutput to ContentUpdateCommentBFFOutput with output: {}", output);
        return output;
    }
}
