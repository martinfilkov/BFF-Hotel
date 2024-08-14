package com.tinqinacademy.bff.core.converters.comment;

import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFOutput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminUpdateCommentOutputToAdminUpdateCommentBFFOutputConverter implements Converter<AdminUpdateCommentOutput, AdminUpdateCommentBFFOutput> {
    @Override
    public AdminUpdateCommentBFFOutput convert(AdminUpdateCommentOutput input) {
        log.info("Start converting from AdminUpdateCommentOutput to AdminUpdateCommentBFFOutput with input: {}", input);

        AdminUpdateCommentBFFOutput output = AdminUpdateCommentBFFOutput.builder()
                .id(input.getId())
                .build();

        log.info("End converting from AdminUpdateCommentOutput to AdminUpdateCommentBFFOutput with output: {}", output);
        return output;
    }
}
