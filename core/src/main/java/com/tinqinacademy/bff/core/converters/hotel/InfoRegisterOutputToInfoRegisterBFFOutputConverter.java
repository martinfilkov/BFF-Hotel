package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.inforregister.InfoRegisterBFFOutput;
import com.tinqinacademy.hotel.api.operations.system.inforregister.InfoRegisterOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InfoRegisterOutputToInfoRegisterBFFOutputConverter implements Converter<InfoRegisterOutput, InfoRegisterBFFOutput> {
    @Override
    public InfoRegisterBFFOutput convert(InfoRegisterOutput input) {
        log.info("Start converting from InfoRegisterOutput to InfoRegisterBFFOutput with input: {}", input);

        InfoRegisterBFFOutput output = InfoRegisterBFFOutput.builder()
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phone(input.getPhone())
                .idCardIssueAuthority(input.getIdCardIssueAuthority())
                .idCardIssueDate(input.getIdCardIssueDate())
                .idCardNumber(input.getIdCardNumber())
                .idCardValidity(input.getIdCardValidity())
                .build();

        log.info("End converting from InfoRegisterOutput to InfoRegisterBFFOutput with output: {}", output);
        return output;
    }
}
