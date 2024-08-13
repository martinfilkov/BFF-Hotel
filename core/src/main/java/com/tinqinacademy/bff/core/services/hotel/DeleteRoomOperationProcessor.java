package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.deleteroom.DeleteRoomBFFOutput;
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
public class DeleteRoomOperationProcessor extends BaseOperationProcessor implements DeleteRoomBFFOperation {
    private final HotelRestClient hotelRestClient;

    public DeleteRoomOperationProcessor(ConversionService conversionService,
                                        Validator validator,
                                        ErrorMapper errorMapper,
                                        HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, DeleteRoomBFFOutput> process(DeleteRoomBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> deleteRoom(input));
    }

    private Either<Errors, DeleteRoomBFFOutput> deleteRoom(DeleteRoomBFFInput input) {
        return Try.of(() -> {
                    log.info("Start deleteRoom with input: {}", input);

                    hotelRestClient.delete(input.getId());

                    DeleteRoomBFFOutput output = DeleteRoomBFFOutput.builder().build();
                    log.info("End deleteRoom with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
