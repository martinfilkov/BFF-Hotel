package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFInput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateRoomBFFInputToUpdateRoomInputConverter implements Converter<UpdateRoomBFFInput, UpdateRoomInput> {
    @Override
    public UpdateRoomInput convert(UpdateRoomBFFInput input) {
        log.info("Start converting from UpdateRoomBFFInput to UpdateRoomInput with input: {}", input);

        UpdateRoomInput output = UpdateRoomInput.builder()
                .bathRoomType(input.getBathRoomType())
                .bedSizes(input.getBedSizes())
                .price(input.getPrice())
                .roomNumber(input.getRoomNumber())
                .build();

        log.info("End converting from UpdateRoomBFFInput to UpdateRoomInput with output: {}", output);
        return output;
    }
}
