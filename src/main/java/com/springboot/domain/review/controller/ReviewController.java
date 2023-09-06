package com.springboot.domain.review.controller;

import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.review.dto.ReviewResponseDto;
import com.springboot.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{placeId}/{categoryCode}")
    public List<ReviewResponseDto> getReviewByCategory(@PathVariable long placeId, @PathVariable String categoryCode) {
        return reviewService.getReviewByCategory(placeId, categoryCode);
    }

    @GetMapping("/{placeId}/{month}/{categoryCode}")
    public List<ReviewResponseDto> getReviewByCategoryAndMonth(@PathVariable long placeId, @PathVariable long month, @PathVariable String categoryCode) {
        return reviewService.getReviewByCategoryAndMonth(placeId, month, categoryCode);
    }
}
