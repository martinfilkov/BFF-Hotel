package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.bookroom.BookRoomBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.bff.domain.configurations.HotelRestExportConfiguration;
import com.tinqinacademy.bff.persistence.JWTContext;
import com.tinqinacademy.hotel.api.operations.hotel.bookroom.BookRoomInput;
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
public class BookRoomOperationProcessor extends BaseOperationProcessor implements BookRoomBFFOperation {
    private final HotelRestExportConfiguration hotelRestExportConfiguration;
    private final JWTContext jwtContext;

    protected BookRoomOperationProcessor(ConversionService conversionService,
                                         Validator validator,
                                         ErrorMapper errorMapper,
                                         HotelRestExportConfiguration hotelRestExportConfiguration, JWTContext jwtContext) {
        super(conversionService, validator, errorMapper);
        this.hotelRestExportConfiguration = hotelRestExportConfiguration;
        this.jwtContext = jwtContext;
    }

    @Override
    public Either<Errors, BookRoomBFFOutput> process(BookRoomBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> bookRoom(input));
    }

    private Either<Errors, BookRoomBFFOutput> bookRoom(BookRoomBFFInput input) {
        return Try.of(() -> {
                    log.info("Start bookRoom input: {}", input);
                    String userId = jwtContext.getUserId();

                    BookRoomInput request = conversionService.convert(input, BookRoomInput.BookRoomInputBuilder.class)
                            .userId(userId)
                            .build();

                    hotelRestExportConfiguration.bookRoom(input.getRoomId(), request);

                    BookRoomBFFOutput output = BookRoomBFFOutput.builder().build();
                    log.info("End bookRoom output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))
                ));
    }
}
