package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.roombyid.RoomByIdBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.roombyid.RoomByIdBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.roombyid.RoomByIdBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.hotel.roombyid.RoomByIdOutput;
import com.tinqinacademy.hotel.restexport.HotelRestClient;
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
public class RoomByIdOperationProcessor extends BaseOperationProcessor implements RoomByIdBFFOperation {
    private final HotelRestClient hotelRestClient;

    public RoomByIdOperationProcessor(ConversionService conversionService,
                                      Validator validator,
                                      ErrorMapper errorMapper,
                                      HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, RoomByIdBFFOutput> process(RoomByIdBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> roomById(input));
    }

    private Either<Errors, RoomByIdBFFOutput> roomById(RoomByIdBFFInput input) {
        return Try.of(() -> {
                    log.info("Start roomById with input: {}", input);

                    RoomByIdOutput requestOutput = hotelRestClient.getRoom(input.getId());

                    RoomByIdBFFOutput output = conversionService.convert(requestOutput, RoomByIdBFFOutput.class);
                    log.info("End roomById with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
