package com.tinqinacademy.bff.core.services;

import com.tinqinacademy.bff.domain.configurations.HotelRestExportConfiguration;
import com.tinqinacademy.hotel.api.operations.hotel.roombyid.RoomByIdOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HotelClientService {
    private final HotelRestExportConfiguration hotelRestExportConfiguration;

    public RoomByIdOutput test() {
        return hotelRestExportConfiguration.getRoom("52458f59-94a6-458d-9315-8f47ba17837d");
    }
}
