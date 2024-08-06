package com.tinqinacademy.bff.domain.configurations;

import com.tinqinacademy.hotel.restexport.HotelRestClient;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "hotel-service", url = "${hotel.service.url}")
public interface HotelRestExportConfiguration extends HotelRestClient {
}
