package com.springboot.domain.place.controller;

import com.springboot.domain.place.dto.PlaceRequestDto;
import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/{place_id}")
    public PlaceResponseDto getPlace(@PathVariable Long place_id) {
        return placeService.getPlace(place_id);
    }

//    @GetMapping("/name")
//    public PlaceResponseDto getPlaceByName(@RequestBody PlaceRequestDto placeRequestDto) {
//        return placeService.getPlaceByName(placeRequestDto);
//    }

//    @GetMapping("/region")
//    public PlaceResponseDto getPlace(@PathVariable Long placeId) {
//        return placeService.getPlace(placeId);
//    }

//    @GetMapping("/all")
//    public PlaceResponseDto getPlace(@PathVariable Long placeId) {
//        return placeService.getPlace(placeId);
//    }
}
