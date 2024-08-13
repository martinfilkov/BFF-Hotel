package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFInputList;
import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFOutput;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInputList;
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
public class RegisterVisitorOperationProcessor extends BaseOperationProcessor implements RegisterVisitorBFFOperation {
    private final HotelRestClient hotelRestClient;

    public RegisterVisitorOperationProcessor(ConversionService conversionService,
                                             Validator validator,
                                             ErrorMapper errorMapper,
                                             HotelRestClient hotelRestClient) {
        super(conversionService, validator, errorMapper);
        this.hotelRestClient = hotelRestClient;
    }

    @Override
    public Either<Errors, RegisterVisitorBFFOutput> process(RegisterVisitorBFFInputList inputList) {
        return validateInput(inputList)
                .flatMap(validated -> registerVisitor(inputList));
    }

    private Either<Errors, RegisterVisitorBFFOutput> registerVisitor(RegisterVisitorBFFInputList inputList) {
        return Try.of(() -> {
                    log.info("Start registerVisitor with input: {}", inputList);
                    RegisterVisitorInputList requestInput = conversionService.convert(inputList, RegisterVisitorInputList.class);

                    hotelRestClient.register(requestInput);

                    RegisterVisitorBFFOutput output = RegisterVisitorBFFOutput.builder().build();
                    log.info("End registerVisitor with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
