package com.springboot.domain.saMonthlySummary.controller;


import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.place.service.PlaceService;
import com.springboot.domain.saMonthlySummary.dto.SaCategorySummaryResponseDto;
import com.springboot.domain.saMonthlySummary.dto.SaPlaceResponseDto;
import com.springboot.domain.saMonthlySummary.service.SaMonthlySummaryService;
import com.springboot.global.result.ResultCode;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sas")
public class SaMonthlySummaryController {

    private final SaMonthlySummaryService saService;

    @GetMapping("/category/{categoryCode}")
    public ResponseEntity<ResultResponse> getCategoryBestSAPlace(@PathVariable String categoryCode) {
        List<SaCategorySummaryResponseDto> responseDtos =  saService.getCategoryBestSAPlace(categoryCode);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BEST_CATEGORY_PLACE_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/category/{categoryCode}/{month}")
    public ResponseEntity<ResultResponse> getMonthlyCategoryBestSAPlace(@PathVariable String categoryCode, @PathVariable int month) {
        List<SaCategorySummaryResponseDto> responseDtos =  saService.getMonthlyCategoryBestSAPlace(categoryCode, month);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BEST_MONTHLY_CATEGORY_PLACE_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<ResultResponse> getPlace(@PathVariable long placeId) {
        List<SaPlaceResponseDto> responseDtos = saService.getPlace(placeId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SA_SUCCESS, responseDtos));
    }

    @GetMapping("/{placeId}/{month}")
    public ResponseEntity<ResultResponse> getMonthlyPlace(@PathVariable long placeId, @PathVariable int month) {
        List<SaPlaceResponseDto> responseDtos = saService.getMonthlyPlace(placeId, month);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MONTHLY_SA_SUCCESS, responseDtos));
    }
}
