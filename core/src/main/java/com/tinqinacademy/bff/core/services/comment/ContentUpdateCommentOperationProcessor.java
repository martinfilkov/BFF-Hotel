package com.tinqinacademy.bff.core.services.comment;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOutputList;
import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.partialupdatecomment.ContentUpdateCommentBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.bff.persistence.JWTContext;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.partialupdatecomment.ContentUpdateCommentOutput;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import feign.FeignException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class ContentUpdateCommentOperationProcessor extends BaseOperationProcessor implements ContentUpdateCommentBFFOperation {
    private final CommentRestClient commentRestClient;
    private final JWTContext jwtContext;

    public ContentUpdateCommentOperationProcessor(ConversionService conversionService,
                                                  Validator validator,
                                                  ErrorMapper errorMapper,
                                                  CommentRestClient commentRestClient,
                                                  JWTContext jwtContext) {
        super(conversionService, validator, errorMapper);
        this.commentRestClient = commentRestClient;
        this.jwtContext = jwtContext;
    }

    @Override
    public Either<Errors, ContentUpdateCommentBFFOutput> process(ContentUpdateCommentBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> contentUpdateComment(input));
    }

    private Either<Errors, ContentUpdateCommentBFFOutput> contentUpdateComment(ContentUpdateCommentBFFInput input) {
        return Try.of(() -> {
                    log.info("Start contentUpdateComment with input: {}", input);

                    ContentUpdateCommentInput requestInput = conversionService.convert(input, ContentUpdateCommentInput.ContentUpdateCommentInputBuilder.class)
                            .userId(jwtContext.getUserId())
                            .build();

                    ContentUpdateCommentOutput requestOutput = commentRestClient.contentUpdate(input.getCommentId(), requestInput);

                    ContentUpdateCommentBFFOutput output = conversionService.convert(requestOutput, ContentUpdateCommentBFFOutput.class);
                    log.info("End contentUpdateComment with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(FeignException.class)), errorMapper::handleFeignException),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
