package com.tinqinacademy.bff.core.services;

import com.tinqinacademy.bff.domain.noconfiguration.HotelServiceRestExport;
import com.tinqinacademy.hotel.api.operations.hotel.roombyid.RoomByIdOutput;
import com.tinqinacademy.hotel.restexport.HotelRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HotelClientService {
    private final HotelRestClient hotelRestClient;
    private final HotelServiceRestExport hotelServiceRestExport;

    public ResponseEntity<RoomByIdOutput> test() {
        return hotelRestClient.getRoom("52458f59-94a6-458d-9315-8f47ba17837d");
    }
}
