package com.tinqinacademy.bff.domain.noconfiguration;

import com.tinqinacademy.hotel.restexport.HotelRestClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "hotel-service", url = "${hotel.service.url}", configuration = FeignConfiguration.class)
public interface HotelServiceRestExport extends HotelRestClient {
}
