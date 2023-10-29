package com.springboot.domain.saMonthlyKeyword.controller;


import com.springboot.domain.saMonthlyKeyword.dto.KeywordListResponseDto;
import com.springboot.domain.saMonthlyKeyword.dto.KeywordPlaceResponseDto;
import com.springboot.domain.saMonthlyKeyword.service.SaMonthlyKeywordService;
import com.springboot.global.result.ResultCode;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/keywords")
public class SaMonthlyKeywordController {

    private final SaMonthlyKeywordService saService;

    @GetMapping("")
    public ResponseEntity<ResultResponse> getKeywordBestPlace(@RequestParam("keyword") String keyword) {
        List<KeywordListResponseDto> responseDtos =  saService.getKeywordBestPlace(keyword);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BEST_CATEGORY_PLACE_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/{month}")
    public ResponseEntity<ResultResponse> getMonthlyKeywordBestPlace(@RequestParam("keyword") String keyword, @PathVariable int month) {
        List<KeywordListResponseDto> responseDtos =  saService.getMonthlyKeywordBestPlace(keyword, month);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BEST_MONTHLY_CATEGORY_PLACE_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<ResultResponse> getPlace(@PathVariable long placeId) {
        KeywordPlaceResponseDto responseDto = saService.getPlace(placeId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SA_SUCCESS, responseDto));
    }

    @GetMapping("/place/{placeId}/{month}")
    public ResponseEntity<ResultResponse> getMonthlyPlace(@PathVariable long placeId, @PathVariable int month) {
        KeywordPlaceResponseDto responseDto = saService.getMonthlyPlace(placeId, month);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MONTHLY_SA_SUCCESS, responseDto));
    }
}
