package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.unbookroom.UnbookRoomBFFOutput;
import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.bff.domain.configurations.HotelRestExportConfiguration;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
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
public class UpdateRoomOperationProcessor extends BaseOperationProcessor implements UpdateRoomBFFOperation {
    private final HotelRestExportConfiguration hotelRestExportConfiguration;

    public UpdateRoomOperationProcessor(ConversionService conversionService,
                                        Validator validator,
                                        ErrorMapper errorMapper,
                                        HotelRestExportConfiguration hotelRestExportConfiguration) {
        super(conversionService, validator, errorMapper);
        this.hotelRestExportConfiguration = hotelRestExportConfiguration;
    }

    @Override
    public Either<Errors, UpdateRoomBFFOutput> process(UpdateRoomBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> updateRoom(input));
    }

    private Either<Errors, UpdateRoomBFFOutput> updateRoom(UpdateRoomBFFInput input) {
        return Try.of(() -> {
                    log.info("Start updateRoom with input: {}", input);

                    UpdateRoomInput requestInput = conversionService.convert(input, UpdateRoomInput.class);
                    UpdateRoomOutput requestOutput = hotelRestExportConfiguration.update(input.getRoomId(), requestInput);

                    UpdateRoomBFFOutput output = conversionService.convert(requestOutput, UpdateRoomBFFOutput.class);
                    log.info("End updateRoom with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
