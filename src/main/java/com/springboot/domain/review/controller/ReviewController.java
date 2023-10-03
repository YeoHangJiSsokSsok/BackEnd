package com.springboot.domain.review.controller;

import com.springboot.domain.review.dto.ReviewResponseDto;
import com.springboot.domain.review.service.ReviewService;
import com.springboot.domain.saMonthlySummary.dto.SaCategorySummaryResponseDto;
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
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{saMonthlySummaryId}/{categoryCode}")
    public ResponseEntity<ResultResponse> getReviewByCategory(@PathVariable long saMonthlySummaryId, @PathVariable String categoryCode) {
        List<ReviewResponseDto> responseDtos = reviewService.getReviewByCategory(saMonthlySummaryId, categoryCode);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CATEGORY_REVIEW_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/month/{saMonthlySummaryId}/{categoryCode}")
    public ResponseEntity<ResultResponse> getReviewByCategoryAndMonth(@PathVariable long saMonthlySummaryId, @PathVariable String categoryCode) {
        List<ReviewResponseDto> responseDtos = reviewService.getReviewByCategoryAndMonth(saMonthlySummaryId, categoryCode);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CATEGORY_MONTHLY_REVIEW_LIST_SUCCESS, responseDtos));
    }
}
