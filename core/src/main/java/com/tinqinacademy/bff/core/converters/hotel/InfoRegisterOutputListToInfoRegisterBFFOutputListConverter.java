package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.inforregister.InfoRegisterBFFOutputList;
import com.tinqinacademy.hotel.api.operations.system.inforregister.InfoRegisterOutputList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InfoRegisterOutputListToInfoRegisterBFFOutputListConverter implements Converter<InfoRegisterOutputList, InfoRegisterBFFOutputList> {
    private final InfoRegisterOutputToInfoRegisterBFFOutputConverter visitorConverter;

    @Override
    public InfoRegisterBFFOutputList convert(InfoRegisterOutputList input) {
        log.info("Start converting from InfoRegisterOutputList to InfoRegisterBFFOutputList with input: {}", input);

        InfoRegisterBFFOutputList output = InfoRegisterBFFOutputList.builder()
                .visitors(input.getVisitors().stream()
                        .map(visitorConverter::convert)
                        .toList())
                .build();

        log.info("End converting from InfoRegisterOutputList to InfoRegisterBFFOutputList with output: {}", output);
        return output;
    }
}
