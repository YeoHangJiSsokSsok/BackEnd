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

    // Member
    MEMBER_NOT_FOUND(400, "M001", "존재 하지 않는 유저입니다."),
    USERNAME_ALREADY_EXIST(400, "M002", "이미 존재하는 사용자 이름입니다."),
    ACCOUNT_MISMATCH(401, "M005", "계정 정보가 일치하지 않습니다."),

    //DailyPlan
    DAILYPLAN_ALREADY_EXIST(400, "D001", "이미 데일리 플랜을 작성하였습니다. PUT으로 update 해주세요"),
    DAILYPLAN_NOT_FOUND(400, "D002", "존재 하지 않는 데일리 플랜입니다"),
    DAILYPLAN_MONTHLIST_NOT_FOUND(400, "D003", "해당 월에 작성된 데일리 플랜이 없습니다"),
    DAILYPLAN_NOT_HAVE_TODOLIST(400, "D004", "해당 데일리 플랜에 작성된 투두리스트가 없습니다"),

    //Places
    PLACE_NOT_FOUND(400, "P001", "존재 하지 않는 장소 입니다."),
    REVIEW_NOT_FOUND(400, "R001", "존재 하지 않는 리뷰 입니다."),
    MONTH_NOT_EXIST(400, "M001", "존재 하지 않는 월 입니다."),
    REGION_NOT_FOUND(400, "P002", "존재 하지 않는 지역 코드 입니다."),
    //Category
    CATEGORY_NOT_FOUND(400, "C001", "존재 하지 않는 카테고리입니다."),
    MONTHLY_SUMMARY_NOT_FOUND(400, "C002", "존재 하지 않는 월 별 summary 입니다.")
    ;

    private final int status;
    private final String code;
    private final String message;
}