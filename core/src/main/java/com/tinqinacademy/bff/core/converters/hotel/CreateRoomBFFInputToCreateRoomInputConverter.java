package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.createroom.CreateRoomBFFInput;
import com.tinqinacademy.hotel.api.operations.system.createroom.CreateRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateRoomBFFInputToCreateRoomInputConverter implements Converter<CreateRoomBFFInput, CreateRoomInput> {
    @Override
    public CreateRoomInput convert(CreateRoomBFFInput input) {
        log.info("Start converting from CreateRoomBFFInput to CreateRoomInput with input: {}", input);

        CreateRoomInput output = CreateRoomInput.builder()
                .bedSizes(input.getBedSizes())
                .bathRoomType(input.getBathRoomType())
                .floor(input.getFloor())
                .roomNumber(input.getRoomNumber())
                .price(input.getPrice())
                .build();

        log.info("End converting from CreateRoomBFFInput to CreateRoomInput with output: {}", output);
        return output;
    }
}
