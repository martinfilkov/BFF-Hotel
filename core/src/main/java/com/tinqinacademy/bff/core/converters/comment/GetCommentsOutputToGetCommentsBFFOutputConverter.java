package com.tinqinacademy.bff.core.converters.comment;

import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOutput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetCommentsOutputToGetCommentsBFFOutputConverter implements Converter<GetCommentsOutput, GetCommentsBFFOutput> {
    @Override
    public GetCommentsBFFOutput convert(GetCommentsOutput input) {
        log.info("Start converting from GetCommentsOutput to GetCommentsBFFOutput with input: {}", input);

        GetCommentsBFFOutput output = GetCommentsBFFOutput.builder()
                .id(input.getId())
                .userId(input.getUserId())
                .content(input.getContent())
                .publishDate(input.getPublishDate())
                .lastEditedBy(input.getLastEditedBy())
                .lastEditedDate(input.getLastEditedDate())
                .build();

        log.info("End converting from GetCommentsOutput to GetCommentsBFFOutput with output: {}", output);
        return output;
    }
}
