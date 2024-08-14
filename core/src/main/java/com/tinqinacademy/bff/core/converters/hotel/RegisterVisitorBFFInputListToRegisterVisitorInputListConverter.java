package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.registervisitor.RegisterVisitorBFFInputList;
import com.tinqinacademy.hotel.api.operations.system.registervisitor.RegisterVisitorInputList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterVisitorBFFInputListToRegisterVisitorInputListConverter implements Converter<RegisterVisitorBFFInputList, RegisterVisitorInputList> {
    private final RegisterVisitorBFFInputToRegisterVisitorInputConverter visitorConverter;

    @Override
    public RegisterVisitorInputList convert(RegisterVisitorBFFInputList inputList) {
        log.info("Start converting from RegisterVisitorBFFInputList to RegisterVisitorInput with input: {}", inputList);

        RegisterVisitorInputList output = RegisterVisitorInputList.builder()
                .startDate(inputList.getStartDate())
                .endDate(inputList.getEndDate())
                .roomNumber(inputList.getRoomNumber())
                .visitors(inputList.getVisitors().stream()
                                .map(visitorConverter::convert)
                                .toList())
                .build();

        log.info("End converting from RegisterVisitorBFFInput to RegisterVisitorInput with output: {}", output);
        return output;
    }
}
