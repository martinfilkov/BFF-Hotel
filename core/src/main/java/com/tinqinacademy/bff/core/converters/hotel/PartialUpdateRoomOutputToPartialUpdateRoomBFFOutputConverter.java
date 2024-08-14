package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateRoomBFFOutput;
import com.tinqinacademy.hotel.api.operations.system.partialupdate.PartialUpdateRoomOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PartialUpdateRoomOutputToPartialUpdateRoomBFFOutputConverter implements Converter<PartialUpdateRoomOutput, PartialUpdateRoomBFFOutput> {
    @Override
    public PartialUpdateRoomBFFOutput convert(PartialUpdateRoomOutput input) {
        log.info("Start converting from PartialUpdateRoomOutput to PartialUpdateRoomBFFOutput with input: {}", input);

        PartialUpdateRoomBFFOutput output = PartialUpdateRoomBFFOutput.builder()
                .id(input.getId())
                .build();

        log.info("End converting from PartialUpdateRoomOutput to PartialUpdateRoomBFFOutput with output: {}", output);
        return output;
    }
}
