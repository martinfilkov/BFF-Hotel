package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateRoomBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.partialupdate.PartialUpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.partialupdate.PartialUpdateRoomOutput;
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
public class PartialUpdateOperationProcessor extends BaseOperationProcessor implements PartialUpdateBFFOperation {
    private final HotelRestClient hotelRestClient;

    protected PartialUpdateOperationProcessor(ConversionService conversionService,
                                              Validator validator,
                                              ErrorMapper errorMapper, HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, PartialUpdateRoomBFFOutput> process(PartialUpdateRoomBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> partialUpdateRoom(input));
    }

    private Either<Errors, PartialUpdateRoomBFFOutput> partialUpdateRoom(PartialUpdateRoomBFFInput input) {
        return Try.of(() -> {
                    log.info("Start partialUpdateRoom with input: {}", input);

                    PartialUpdateRoomInput requestInput = conversionService.convert(input, PartialUpdateRoomInput.class);
                    PartialUpdateRoomOutput requestOutput = hotelRestClient.partialUpdate(input.getRoomId(), requestInput);

                    PartialUpdateRoomBFFOutput output = conversionService.convert(requestOutput, PartialUpdateRoomBFFOutput.class);
                    log.info("End partialUpdate with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(instanceOf(FeignException.class)), errorMapper::handleFeignException),
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
