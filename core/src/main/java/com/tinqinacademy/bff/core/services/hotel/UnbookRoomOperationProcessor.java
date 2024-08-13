package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
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
public class UnbookRoomOperationProcessor extends BaseOperationProcessor implements UnbookRoomBFFOperation {
    private final HotelRestClient hotelRestClient;

    public UnbookRoomOperationProcessor(ConversionService conversionService,
                                        Validator validator,
                                        ErrorMapper errorMapper,
                                        HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, UnbookRoomBFFOutput> process(UnbookRoomBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> unbookRoom(input));
    }

    private Either<Errors, UnbookRoomBFFOutput> unbookRoom(UnbookRoomBFFInput input) {
        return Try.of(() -> {
                    log.info("Start unbookRoom with input: {}", input);

                    hotelRestClient.unbookRoom(input.getBookingId());

                    UnbookRoomBFFOutput output = UnbookRoomBFFOutput.builder().build();
                    log.info("End unbookRoom with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
