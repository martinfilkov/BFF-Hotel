package com.tinqinacademy.bff.rest.controllers;

import com.tinqinacademy.bff.core.services.HotelClientService;
import com.tinqinacademy.bff.domain.noconfiguration.HotelServiceRestExport;
import com.tinqinacademy.hotel.api.operations.base.URLMapping;
import com.tinqinacademy.hotel.api.operations.hotel.roombyid.RoomByIdOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final HotelClientService hotelClientService;

    @GetMapping(URLMapping.GET_ROOM)
    public ResponseEntity<RoomByIdOutput> test(){
        return hotelClientService.test();
    }
}
