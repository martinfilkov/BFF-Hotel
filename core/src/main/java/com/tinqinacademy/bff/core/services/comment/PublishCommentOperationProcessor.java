package com.tinqinacademy.bff.core.services.comment;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.publishcomment.PublishCommentBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentInput;
import com.tinqinacademy.comment.api.operations.hotel.publishcomment.PublishCommentOutput;
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
public class PublishCommentOperationProcessor extends BaseOperationProcessor implements PublishCommentBFFOperation {
    private final CommentRestClient commentRestClient;
    private final HotelRestClient hotelRestClient;

    public PublishCommentOperationProcessor(ConversionService conversionService,
                                            Validator validator,
                                            ErrorMapper errorMapper,
                                            CommentRestClient commentRestClient, HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.commentRestClient = commentRestClient;
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, PublishCommentBFFOutput> process(PublishCommentBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> publishComment(input));
    }

    private Either<Errors, PublishCommentBFFOutput> publishComment(PublishCommentBFFInput input) {
        return Try.of(() -> {
                    log.info("Start publishComment with input: {}", input);
                    hotelRestClient.getRoom(input.getRoomId());

                    PublishCommentInput requestInput = conversionService.convert(input, PublishCommentInput.class);

                    PublishCommentOutput requestOutput = commentRestClient.publishComment(input.getRoomId(), requestInput);

                    PublishCommentBFFOutput output = conversionService.convert(requestOutput, PublishCommentBFFOutput.class);
                    log.info("End publishComment with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(FeignException.class)), errorMapper::handleFeignException),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
