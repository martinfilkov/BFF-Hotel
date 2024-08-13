package com.tinqinacademy.bff.core.services.hotel;

import com.tinqinacademy.bff.api.operations.base.Errors;
import com.tinqinacademy.bff.api.operations.hotel.inforregister.GetRegisterInfoBFFOperation;
import com.tinqinacademy.bff.api.operations.hotel.inforregister.InfoRegisterBFFInput;
import com.tinqinacademy.bff.api.operations.hotel.inforregister.InfoRegisterBFFOutputList;
import com.tinqinacademy.bff.core.ErrorMapper;
import com.tinqinacademy.bff.core.services.BaseOperationProcessor;
import com.tinqinacademy.bff.domain.configurations.HotelRestExportConfiguration;
import com.tinqinacademy.hotel.api.operations.system.inforregister.InfoRegisterOutput;
import com.tinqinacademy.hotel.api.operations.system.inforregister.InfoRegisterOutputList;
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
public class GetRegisterInfoOperationProcessor extends BaseOperationProcessor implements GetRegisterInfoBFFOperation {
    private final HotelRestExportConfiguration hotelRestExportConfiguration;

    public GetRegisterInfoOperationProcessor(ConversionService conversionService,
                                             Validator validator,
                                             ErrorMapper errorMapper,
                                             HotelRestExportConfiguration hotelRestExportConfiguration) {
        super(conversionService, validator, errorMapper);
        this.hotelRestExportConfiguration = hotelRestExportConfiguration;
    }

    @Override
    public Either<Errors, InfoRegisterBFFOutputList> process(InfoRegisterBFFInput input) {
        return validateInput(input)
                .flatMap(validated -> getRegisterInfo(input));
    }

    private Either<Errors, InfoRegisterBFFOutputList> getRegisterInfo(InfoRegisterBFFInput input) {
        return Try.of(() -> {
                    log.info("Start getRegisterInfo with input: {}", input);
                    InfoRegisterOutputList requestOutput = hotelRestExportConfiguration.infoRegistry(
                            input.getStartDate(),
                            input.getEndDate(),
                            input.getRoomNumber(),
                            input.getFirstName(),
                            input.getLastName(),
                            input.getPhone(),
                            input.getIdCardNumber(),
                            input.getIdCardValidity(),
                            input.getIdCardIssueAuthority(),
                            input.getIdCardIssueDate()
                    );

                    InfoRegisterBFFOutputList output = conversionService.convert(requestOutput, InfoRegisterBFFOutputList.class);
                    log.info("End getRegisterInfo with output: {}", output);
                    return output;
                })
                .toEither()
                .mapLeft(throwable -> Match(throwable).of(
                        Case($(), ex -> errorMapper.handleError(ex, HttpStatus.BAD_REQUEST))));
    }
}
