package com.tinqinacademy.bff.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BFFController extends BaseController {

    @GetMapping("/api/test/authenticated")
    public String authenticatedTest(){
        return "Authenticated";
    }

    @GetMapping("/api/test/hotel")
    public String nonAuthenticatedTest(){
        return "Non authenticated";
    }
}
