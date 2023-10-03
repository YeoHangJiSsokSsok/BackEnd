package com.springboot.domain.place.controller;

import com.springboot.domain.place.dto.PlaceRequestDto;
import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.place.service.PlaceService;
import com.springboot.global.result.ResultCode;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/{placeId}")
    public ResponseEntity<ResultResponse> getPlace(@PathVariable Long placeId) {
        PlaceResponseDto responseDto = placeService.getPlace(placeId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_PLACE_SUCCESS, responseDto));
    }

    @GetMapping("/name")
    public ResponseEntity<ResultResponse> getPlaceByRegionAndName(@RequestParam("name") String name) {
        List<PlaceResponseDto> responseDtos = placeService.getPlaceByRegionAndName(name);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_PLACE_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/region")
    public ResponseEntity<ResultResponse> getPlaceByRegion(@RequestParam("name") String name) {
        List<PlaceResponseDto> responseDtos =  placeService.getPlaceByRegion(name);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_PLACE_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("")
    public ResponseEntity<ResultResponse> getAllPlace() {
        List<PlaceResponseDto> responseDtos = placeService.getAllPlace();
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_PLACE_LIST_SUCCESS, responseDtos));
    }
}
