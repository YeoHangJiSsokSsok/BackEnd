package com.springboot.domain.saMonthlySummary.controller;


import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.place.service.PlaceService;
import com.springboot.domain.saMonthlySummary.dto.SaCategorySummaryResponseDto;
import com.springboot.domain.saMonthlySummary.dto.SaPlaceResponseDto;
import com.springboot.domain.saMonthlySummary.service.SaMonthlySummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sa")
public class SaMonthlySummaryController {

    private final SaMonthlySummaryService saService;

    @GetMapping("/category/{categoryCode}")
    public List<SaCategorySummaryResponseDto> getCategoryBestSAPlace(@PathVariable String categoryCode) {
        return saService.getCategoryBestSAPlace(categoryCode);
    }

    @GetMapping("/place/{placeId}")
    public List<SaPlaceResponseDto> getPlace(@PathVariable long placeId) {
        return saService.getPlace(placeId);
    }

    @GetMapping("/{placeId}/{month}")
    public List<SaPlaceResponseDto> getMonthlyPlace(@PathVariable long placeId, @PathVariable int month) {
        return saService.getMonthlyPlace(placeId, month);
    }
}
