package com.tinqinacademy.bff.core.converters.comment;

import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOutputList;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetCommentsOutputListToGetCommentsBFFOutputListConverter implements Converter<GetCommentsOutputList, GetCommentsBFFOutputList> {
    private final GetCommentsOutputToGetCommentsBFFOutputConverter commentsConverter;

    @Override
    public GetCommentsBFFOutputList convert(GetCommentsOutputList input) {
        log.info("Start converting from GetCommentsOutputList to GetCommentsBFFOutputList with input: {}", input);

        GetCommentsBFFOutputList output = GetCommentsBFFOutputList.builder()
                .comments(input.getComments().stream()
                        .map(commentsConverter::convert)
                        .toList())
                .build();

        log.info("End converting from GetCommentsOutputList to GetCommentsBFFOutputList with output: {}", output);
        return output;
    }
}
