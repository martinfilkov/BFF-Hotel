package com.tinqinacademy.bff.core.services.comment;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFInput;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.getcomments.GetCommentsBFFOutputList;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.bff.persistence.JWTContext;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutput;
import com.tinqinacademy.comment.api.operations.hotel.getcomments.GetCommentsOutputList;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import com.tinqinacademy.hotel.restexport.HotelRestClient;
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
public class GetCommentsOperationProcessor extends BaseOperationProcessor implements GetCommentsBFFOperation {
    private final CommentRestClient commentRestClient;
    private final HotelRestClient hotelRestClient;

    public GetCommentsOperationProcessor(ConversionService conversionService,
                                         Validator validator,
                                         ErrorMapper errorMapper,
                                         CommentRestClient commentRestClient,
                                         JWTContext jwtContext, HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.commentRestClient = commentRestClient;
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, GetCommentsBFFOutputList> process(GetCommentsBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> getComments(input));
    }

    private Either<Errors, GetCommentsBFFOutputList> getComments(GetCommentsBFFInput input) {
        return Try.of(() -> {
                    log.info("Start getComments with input: {}", input);
                    hotelRestClient.getRoom(input.getRoomId());

                    GetCommentsOutputList requestOutput = commentRestClient.getComments(input.getRoomId());

                    GetCommentsBFFOutputList output = conversionService.convert(requestOutput, GetCommentsBFFOutputList.class);
                    log.info("End getComments with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(FeignException.class)), errorMapper::handleFeignException),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
