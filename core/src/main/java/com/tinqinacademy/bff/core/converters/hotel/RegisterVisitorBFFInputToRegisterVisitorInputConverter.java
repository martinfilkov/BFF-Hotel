package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFInput;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RegisterVisitorBFFInputToRegisterVisitorInputConverter implements Converter<RegisterVisitorBFFInput, RegisterVisitorInput> {
    @Override
    public RegisterVisitorInput convert(RegisterVisitorBFFInput input) {
        log.info("Start converting from RegisterVisitorBFFInput to RegisterVisitorInput with input: {}", input);

        RegisterVisitorInput output = RegisterVisitorInput.builder()
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .birthDate(input.getBirthDate())
                .phone(input.getPhone())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .idCardValidity(input.getIdCardValidity())
                .idCardNumber(input.getIdCardNumber())
                .build();

        log.info("End converting from RegisterVisitorBFFInput to RegisterVisitorInput with output: {}", output);
        return output;
    }
}
