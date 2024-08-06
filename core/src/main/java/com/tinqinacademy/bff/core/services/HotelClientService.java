package com.tinqinacademy.bff.core.services;

import com.tinqinacademy.hotel.api.operations.hotel.roombyid.RoomByIdOutput;
import com.tinqinacademy.hotel.restexport.HotelRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HotelClientService {
    private final HotelRestClient hotelRestClient;

    public RoomByIdOutput test() {
        return hotelRestClient.getRoom("52458f59-94a6-458d-9315-8f47ba17837d");
    }
}
