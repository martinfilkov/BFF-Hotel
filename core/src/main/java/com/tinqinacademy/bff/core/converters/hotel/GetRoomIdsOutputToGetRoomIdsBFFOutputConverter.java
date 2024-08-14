package com.tinqinacademy.bff.core.converters.hotel;

import com.tinqinacademy.bff.api.operations.hotel.getroomids.GetRoomIdsBFFOutput;
import com.tinqinacademy.hotel.api.operations.hotel.getroomids.GetRoomIdsOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetRoomIdsOutputToGetRoomIdsBFFOutputConverter implements Converter<GetRoomIdsOutput, GetRoomIdsBFFOutput> {
    @Override
    public GetRoomIdsBFFOutput convert(GetRoomIdsOutput input) {
        log.info("Start converting from GetRoomIdsOutput to GetRoomIdsBFFOutput with input: {}", input);

        GetRoomIdsBFFOutput output = GetRoomIdsBFFOutput.builder()
                .ids(input.getIds())
                .build();

        log.info("End converting from GetRoomIdsOutput to GetRoomIdsBFFOutput with output: {}", output);
        return output;
    }
}
