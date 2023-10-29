package com.springboot.domain.review.controller;

import com.springboot.domain.review.dto.ReviewKeywordResponseDto;
import com.springboot.domain.review.dto.ReviewSaResponseDto;
import com.springboot.domain.review.service.ReviewService;
import com.springboot.global.result.ResultCode;
import com.springboot.global.result.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{saMonthlySummaryId}/{categoryCode}")
    public ResponseEntity<ResultResponse> getReviewByCategory(@PathVariable long saMonthlySummaryId, @PathVariable String categoryCode) {
        List<ReviewSaResponseDto> responseDtos = reviewService.getReviewByCategory(saMonthlySummaryId, categoryCode);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CATEGORY_REVIEW_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/month/{saMonthlySummaryId}/{categoryCode}")
    public ResponseEntity<ResultResponse> getReviewByCategoryAndMonth(@PathVariable long saMonthlySummaryId, @PathVariable String categoryCode) {
        List<ReviewSaResponseDto> responseDtos = reviewService.getReviewByCategoryAndMonth(saMonthlySummaryId, categoryCode);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CATEGORY_MONTHLY_REVIEW_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/{saMonthlySummaryId}")
    public ResponseEntity<ResultResponse> getReviewByKeyword(@PathVariable long saMonthlySummaryId, @RequestParam("keyword") String keyword) {
        List<ReviewKeywordResponseDto> responseDtos = reviewService.getReviewByKeyword(saMonthlySummaryId, keyword);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_KEYWORD_REVIEW_LIST_SUCCESS, responseDtos));
    }

    @GetMapping("/month/{saMonthlySummaryId}")
    public ResponseEntity<ResultResponse> getReviewByKeywordAndMonth(@PathVariable long saMonthlySummaryId, @RequestParam("keyword") String keyword) {
        List<ReviewKeywordResponseDto> responseDtos = reviewService.getReviewByKeywordAndMonth(saMonthlySummaryId, keyword);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_CATEGORY_MONTHLY_REVIEW_LIST_SUCCESS, responseDtos));
    }
}
