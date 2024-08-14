package com.tinqinacademy.bff.core.services.comment;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.comment.deletecomment.DeleteCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.deletecomment.DeleteCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.deletecomment.DeleteCommentBFFOutput;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOutputList;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import feign.FeignException;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class DeleteCommentOperationProcessor extends BaseOperationProcessor implements DeleteCommentBFFOperation {
    private final CommentRestClient commentRestClient;

    public DeleteCommentOperationProcessor(ConversionService conversionService,
                                           Validator validator,
                                           ErrorMapper errorMapper,
                                           CommentRestClient commentRestClient) {
        super(conversionService, validator, errorMapper);
        this.commentRestClient = commentRestClient;
    }

    @Override
    public Either<Errors, DeleteCommentBFFOutput> process(DeleteCommentBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> deleteComment(input));
    }

    private Either<Errors, DeleteCommentBFFOutput> deleteComment(DeleteCommentBFFInput input) {
        return Try.of(() -> {
                    log.info("Start deleteComment with input: {}", input);

                    commentRestClient.deleteComment(input.getCommentId());

                    DeleteCommentBFFOutput output = DeleteCommentBFFOutput.builder().build();

                    log.info("Start deleteComment with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(FeignException.class)), errorMapper::handleFeignException),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
