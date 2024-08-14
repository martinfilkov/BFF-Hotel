package com.tinqinacademy.bff.core.services.comment;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFInput;
import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFOperation;
import com.tinqinacademy.bff.api.operations.comment.updatecomment.AdminUpdateCommentBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.bff.persistence.JWTContext;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentInput;
import com.tinqinacademy.comment.api.operations.system.updatecomment.AdminUpdateCommentOutput;
import com.tinqinacademy.comment.restexport.CommentRestClient;
import com.tinqinacademy.hotel.api.operations.hotel.roombyid.RoomByIdOutput;
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
public class AdminUpdateCommentOperationProcessor extends BaseOperationProcessor implements AdminUpdateCommentBFFOperation {
    private final CommentRestClient commentRestClient;
    private final HotelRestClient hotelRestClient;
    private final JWTContext jwtContext;

    public AdminUpdateCommentOperationProcessor(ConversionService conversionService, Validator validator, ErrorMapper errorMapper, CommentRestClient commentRestClient, HotelRestClient hotelRestClient, JWTContext jwtContext) {
        super(conversionService, validator, errorMapper);
        this.commentRestClient = commentRestClient;
        this.hotelRestClient = hotelRestClient;
        this.jwtContext = jwtContext;
    }

    @Override
    public Either<Errors, AdminUpdateCommentBFFOutput> process(AdminUpdateCommentBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> adminUpdateComment(input));
    }

    private Either<Errors, AdminUpdateCommentBFFOutput> adminUpdateComment(AdminUpdateCommentBFFInput input) {
        return Try.of(() -> {
                    log.info("Start adminUpdateComment with input: {}", input);
                    hotelRestClient.getRoom(input.getRoomId());

                    AdminUpdateCommentInput requestInput = conversionService.convert(input, AdminUpdateCommentInput.AdminUpdateCommentInputBuilder.class)
                            .userId(jwtContext.getUserId())
                            .build();
                    AdminUpdateCommentOutput requestOutput = commentRestClient.adminUpdateComment(input.getCommentId(), requestInput);

                    AdminUpdateCommentBFFOutput output = conversionService.convert(requestOutput, AdminUpdateCommentBFFOutput.class);
                    log.info("End adminUpdateComment with output: {}", output);
                    return output;

                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(FeignException.class)), errorMapper::handleFeignException),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
