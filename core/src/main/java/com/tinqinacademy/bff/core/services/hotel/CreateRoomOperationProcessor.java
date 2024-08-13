package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.bff.domain.configurations.HotelRestExportConfiguration;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
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
public class CreateRoomOperationProcessor extends BaseOperationProcessor implements CreateRoomBFFOperation {
    private final HotelRestExportConfiguration hotelRestExportConfiguration;

    public CreateRoomOperationProcessor(ConversionService conversionService,
                                        Validator validator,
                                        ErrorMapper errorMapper,
                                        HotelRestExportConfiguration hotelRestExportConfiguration) {
        super(conversionService, validator, errorMapper);
        this.hotelRestExportConfiguration = hotelRestExportConfiguration;
    }

    @Override
    public Either<Errors, CreateRoomBFFOutput> process(CreateRoomBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> createRoom(input));
    }

    private Either<Errors, CreateRoomBFFOutput> createRoom(CreateRoomBFFInput input) {
        return Try.of(() -> {
                    log.info("Start createRoom with input: {}", input);

                    CreateRoomInput requestInput = conversionService.convert(input, CreateRoomInput.class);
                    CreateRoomOutput requestOutput = hotelRestExportConfiguration.create(requestInput);

                    CreateRoomBFFOutput output = conversionService.convert(requestOutput, CreateRoomBFFOutput.class);
                    log.info("End createRoom with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
