package com.springboot.domain.review.service;

import com.springboot.domain.review.dto.ReviewResponseDto;
import com.springboot.domain.review.entity.Reviews;
import com.springboot.domain.review.entity.ReviewsRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private  final ReviewsRepository reviewsRepository;
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getReviewByCategory(Long placeId, String code) {

        List<Reviews> reviews = reviewsRepository.findAll();
        if (reviews.isEmpty()) {
            throw new EntityNotFoundException(REVIEW_NOT_FOUND, "데이터베이스에 리뷰가 없습니다." );
        }

        List<ReviewResponseDto> list = new ArrayList<>();

        switch (code) {
            case "C001":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity->entity.getMood() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getMood())
                                .build())
                        .collect(Collectors.toList());
                break;
            case "C002":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity->entity.getTransport() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getTransport())
                                .build())
                        .collect(Collectors.toList());
                break;
            case "C003":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity->entity.getCongestion() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getCongestion())
                                .build())
                        .collect(Collectors.toList());

                break;
            case "C004":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity->entity.getInfra() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getInfra())
                                .build())
                        .collect(Collectors.toList());

                break;
            default:
                throw new EntityNotFoundException(CATEGORY_NOT_FOUND, "해당 코드의 카테고리가 없습니다 : " + code);
        }
        if (list.isEmpty()) {
            throw new EntityNotFoundException(REVIEW_NOT_FOUND, "해당 장소에 대한 리뷰가 없습니다 : " + placeId );
        }
        return list;
    }

    public List<ReviewResponseDto> getReviewByCategoryAndMonth(long placeId, long month, String code) {

        List<Reviews> reviews = reviewsRepository.findAll();

        List<ReviewResponseDto> list = new ArrayList<>();
        switch (code) {
            case "C001":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity -> entity.getSaMonthlySummary().getMonth() == month)
                        .filter(entity->entity.getMood() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getMood())
                                .build())
                        .collect(Collectors.toList());
                break;
            case "C002":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity -> entity.getSaMonthlySummary().getMonth() == month)
                        .filter(entity->entity.getTransport() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getTransport())
                                .build())
                        .collect(Collectors.toList());
                break;
            case "C003":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity -> entity.getSaMonthlySummary().getMonth() == month)
                        .filter(entity->entity.getCongestion() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getCongestion())
                                .build())
                        .collect(Collectors.toList());

                break;
            case "C004":
                list = reviews.stream()
                        .filter(entity-> entity.getSaMonthlySummary().getPlace().getId() == placeId)
                        .filter(entity -> entity.getSaMonthlySummary().getMonth() == month)
                        .filter(entity->entity.getInfra() != 2)
                        .map(entity -> ReviewResponseDto.builder()
                                .entity(entity)
                                .state(entity.getInfra())
                                .build())
                        .collect(Collectors.toList());

                break;
            default:
                throw new EntityNotFoundException(CATEGORY_NOT_FOUND, "해당 코드의 카테고리가 없습니다 : " + code);
        }
        if (list.isEmpty()) {
            throw new EntityNotFoundException(REVIEW_NOT_FOUND, "해당 장소에 대한 리뷰가 없습니다 : " + placeId );
        }
        return list;
    }
}
