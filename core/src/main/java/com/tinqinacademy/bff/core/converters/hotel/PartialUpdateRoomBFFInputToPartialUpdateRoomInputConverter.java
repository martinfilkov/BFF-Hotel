package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.partialupdate.PartialUpdateRoomBFFInput;
import com.tinqinacademy.hotel.api.operations.system.partialupdate.PartialUpdateRoomInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PartialUpdateRoomBFFInputToPartialUpdateRoomInputConverter implements Converter<PartialUpdateRoomBFFInput, PartialUpdateRoomInput> {
    @Override
    public PartialUpdateRoomInput convert(PartialUpdateRoomBFFInput input) {
        log.info("Start converting from PartialUpdateRoomBFFInput to PartialUpdateRoomInput with input: {}", input);

        PartialUpdateRoomInput output = PartialUpdateRoomInput.builder()
                .bathRoomType(input.getBathRoomType())
                .roomNumber(input.getRoomNumber())
                .bedSizes(input.getBedSizes())
                .price(input.getPrice())
                .build();

        log.info("End converting from PartialUpdateRoomBFFInput to PartialUpdateRoomInput with input: {}", input);
        return output;
    }
}
