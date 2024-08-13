package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFOutput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateRoomOutputToCreateRoomBFFOutputConverter implements Converter<CreateRoomOutput, CreateRoomBFFOutput> {
    @Override
    public CreateRoomBFFOutput convert(CreateRoomOutput input) {
        log.info("Start converting from CreateRoomOutput to CreateRoomBFFOutput with input: {}", input);

        CreateRoomBFFOutput output = CreateRoomBFFOutput.builder()
                .id(input.getId())
                .build();

        log.info("End converting from CreateRoomOutput to CreateRoomBFFOutput with output: {}", output);
        return output;
    }
}
