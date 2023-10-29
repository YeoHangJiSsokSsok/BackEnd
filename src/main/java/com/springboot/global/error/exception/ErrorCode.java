package com.springboot.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCode Convention
 * - 도메인 별로 나누어 관리
 * - [주체_이유] 형태로 생성
 * - 코드는 도메인명 앞에서부터 1~2글자로 사용
 * - 메시지는 "~~다."로 마무리
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //Places
    PLACE_NOT_FOUND(400, "P001", "존재 하지 않는 장소 입니다."),
    REGION_NOT_FOUND(400, "P002", "존재 하지 않는 지역 입니다."),
    REVIEW_NOT_FOUND(400, "R001", "존재 하지 않는 리뷰 입니다."),

    MONTH_NOT_EXIST(400, "M001", "존재 하지 않는 월 입니다."),

    //Category
    CATEGORY_NOT_FOUND(400, "C001", "존재 하지 않는 카테고리 입니다."),
    MONTHLY_SUMMARY_NOT_FOUND(400, "S001", "존재 하지 않는 월 별 summary 입니다."),

    //키워드
    KEYWORD_NOT_FOUND(400, "K001" , "존재하지 않는 키워드 입니다."),
    KEYWORD_LIST_NOT_FOUND(400, "K002" , "키워드 리스트가 존재하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;
}