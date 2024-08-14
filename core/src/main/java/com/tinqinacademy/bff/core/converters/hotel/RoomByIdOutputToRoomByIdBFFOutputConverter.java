package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.roombyid.RoomByIdBFFOutput;
import com.tinqinacademy.hotel.api.operations.hotel.roombyid.RoomByIdOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RoomByIdOutputToRoomByIdBFFOutputConverter implements Converter<RoomByIdOutput, RoomByIdBFFOutput> {
    @Override
    public RoomByIdBFFOutput convert(RoomByIdOutput input) {
        log.info("Start converting from RoomByIdOutput to RoomByIdBFFOutput");

        RoomByIdBFFOutput output = RoomByIdBFFOutput.builder()
                .id(input.getId())
                .price(input.getPrice())
                .floor(input.getFloor())
                .bedSizes(input.getBedSizes())
                .bathroomType(input.getBathroomType())
                .datesOccupied(input.getDatesOccupied())
                .build();

        log.info("End converting from RoomByIdOutput to RoomByIdBFFOutput");
        return output;
    }
}
