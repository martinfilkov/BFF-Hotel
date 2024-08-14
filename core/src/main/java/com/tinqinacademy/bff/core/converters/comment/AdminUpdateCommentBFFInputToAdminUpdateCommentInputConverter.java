package com.tinqinacademy.bff.core.converters.comment;

import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminUpdateCommentBFFInputToAdminUpdateCommentInputConverter implements Converter<AdminUpdateCommentBFFInput, AdminUpdateCommentInput> {
    @Override
    public AdminUpdateCommentInput convert(AdminUpdateCommentBFFInput input) {
        log.info("Start converting from AdminUpdateCommentBFFInput to AdminUpdateCommentInput with input: {}", input);

        AdminUpdateCommentInput output = AdminUpdateCommentInput.builder()
                .commentId(input.getCommentId())
                .content(input.getContent())
                .roomId(input.getRoomId())
                .userId(input.getUserId())
                .build();

        log.info("End converting from AdminUpdateCommentBFFInput to AdminUpdateCommentInput with output: {}", output);
        return output;
    }
}
