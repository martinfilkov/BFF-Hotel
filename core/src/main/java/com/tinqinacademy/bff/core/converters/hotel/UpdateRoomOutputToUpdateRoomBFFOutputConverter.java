package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.updateroom.UpdateRoomBFFOutput;
import com.tinqinacademy.hotel.api.operations.system.updateroom.UpdateRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UpdateRoomOutputToUpdateRoomBFFOutputConverter implements Converter<UpdateRoomOutput, UpdateRoomBFFOutput> {
    @Override
    public UpdateRoomBFFOutput convert(UpdateRoomOutput input) {
        log.info("Start converting from UpdateRoomOutput to UpdateRoomBFFOutput with input: {}", input);

        UpdateRoomBFFOutput output = UpdateRoomBFFOutput.builder()
                .id(input.getId())
                .build();

        log.info("End converting from UpdateRoomOutput to UpdateRoomBFFOutput with output: {}", output);
        return output;
    }
}
