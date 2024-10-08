package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.getroomids.GetRoomIdsBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.getroomids.GetRoomIdsBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.getroomids.GetRoomIdsBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.getroomids.GetRoomIdsOutput;
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
public class GetRoomIdsOperationProcessor extends BaseOperationProcessor implements GetRoomIdsBFFOperation {
    private final HotelRestClient hotelRestClient;

    public GetRoomIdsOperationProcessor(ConversionService conversionService,
                                        Validator validator,
                                        ErrorMapper errorMapper,
                                        HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, GetRoomIdsBFFOutput> process(GetRoomIdsBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> getRoomIds(input));
    }

    private Either<Errors, GetRoomIdsBFFOutput> getRoomIds(GetRoomIdsBFFInput input) {
        return Try.of(() -> {
                    log.info("Start getRoomIds with input: {}", input);

                    GetRoomIdsOutput requestOutput = hotelRestClient.getIds(input.getStartDate(),
                            input.getEndDate(),
                            input.getBedSize(),
                            input.getBathroomType());

                    GetRoomIdsBFFOutput output = conversionService.convert(requestOutput, GetRoomIdsBFFOutput.class);
                    log.info("End getRoomIds with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(FeignException.class)), errorMapper::handleFeignException),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
