package com.springboot.domain.saMonthlyKeyword.dto;

import com.springboot.domain.place.entity.Places;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordListResponseDto {
    private long placeId;

    private String region;

    private String name;

    private String address;

    private String photoUrl;

    private int keywordCount = 0;

    @Builder
    public KeywordListResponseDto(Places place, int positiveNumber, int totalNumber) {
        this.placeId = place.getId();
        this.region = place.getRegion();
        this.name = place.getName();
        this.address = place.getAddress();
        this.photoUrl = place.getPhotoUrl();
    }
    public void addKeywordCount(int keywordCount)
    {
        this.keywordCount += keywordCount;
    }
}
