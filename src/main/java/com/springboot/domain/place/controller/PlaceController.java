package com.springboot.domain.place.controller;

import com.springboot.domain.place.dto.PlaceRequestDto;
import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/name")
    public List<PlaceResponseDto> getPlaceByRegionAndName(@RequestBody PlaceRequestDto placeRequestDto) {
        return placeService.getPlaceByRegionAndName(placeRequestDto);
    }

    @GetMapping("/region")
    public List<PlaceResponseDto> getPlaceByRegion(@RequestBody PlaceRequestDto placeRequestDto) {
        return placeService.getPlaceByRegion(placeRequestDto);
    }

    @GetMapping("/all")
    public List<PlaceResponseDto> getAllPlace() {
        return placeService.getAllPlace();
    }
}
