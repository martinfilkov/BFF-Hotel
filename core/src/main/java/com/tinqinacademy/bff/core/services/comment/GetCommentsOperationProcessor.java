package com.tinqinacademy.bff.core.services.comment;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFInput;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOutputList;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static io.vavr.API.*;

@Slf4j
@Service
public class GetCommentsOperationProcessor extends BaseOperationProcessor implements GetCommentsBFFOperation {
    private final CommentRestClient commentRestClient;

    public GetCommentsOperationProcessor(ConversionService conversionService,
                                         Validator validator,
                                         ErrorMapper errorMapper,
                                         CommentRestClient commentRestClient) {
        super(conversionService, validator, errorMapper);
        this.commentRestClient = commentRestClient;
    }

    @Override
    public Either<Errors, GetCommentsBFFOutputList> process(GetCommentsBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> getComments(input));
    }

    private Either<Errors, GetCommentsBFFOutputList> getComments(GetCommentsBFFInput input) {
        return Try.of(() -> {
                    log.info("Start getComments with input: {}", input);

                    GetCommentsOutputList requestOutput = commentRestClient.getComments(input.getRoomId());

                    GetCommentsBFFOutputList output = conversionService.convert(requestOutput, GetCommentsBFFOutputList.class);
                    log.info("End getComments with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
