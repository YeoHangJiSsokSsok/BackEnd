package com.springboot.global.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    //Place
    GET_PLACE_SUCCESS(200, "P001", "장소를 조회했습니다."),
    GET_PLACE_LIST_SUCCESS(200, "P002", "장소 리스트를 조회했습니다."),

    //SaMonthlySummary
    GET_BEST_CATEGORY_PLACE_LIST_SUCCESS(200, "S001", "카테고리 별 베스트 장소 리스트를 조회했습니다."),

    GET_BEST_MONTHLY_CATEGORY_PLACE_LIST_SUCCESS(200, "S002", "월 별 카테고리 별 베스트 장소 리스트를 조회했습니다."),
    GET_MONTHLY_SA_SUCCESS(200, "S002", "장소 월 별 감성분석 결과를 조회했습니다"),
    GET_SA_SUCCESS(200, "S003", "장소 감성분석 결과를 조회했습니다."),


    //Reviews
    GET_CATEGORY_REVIEW_LIST_SUCCESS(200, "S001", "감성분석 결과의 해당 카테고리 리뷰 리스트를 조회했습니다."),
    GET_CATEGORY_MONTHLY_REVIEW_LIST_SUCCESS(200, "S002", "월 별 감성분석 결과의 해당 카테고리 리뷰 리스트를 조회했습니다")
    ;
    private final int status;
    private final String code;
    private final String message;
}