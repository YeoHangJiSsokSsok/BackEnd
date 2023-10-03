package com.springboot.domain.saMonthlySummary.dto;

import com.springboot.domain.place.entity.Places;
import com.springboot.domain.saMonthlySummary.entity.SaMonthlySummary;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaCategorySummaryResponseDto {
    private long placeId;

    private String region;

    private String name;
    private int positiveNumber = 0;

    private int totalNumber = 0;
    private double proportion;

    @Builder
    public SaCategorySummaryResponseDto(Places place, int positiveNumber, int totalNumber) {
        this.placeId = place.getId();
        this.region = place.getRegion();
        this.name = place.getName();
    }

    public void addPositiveNumber(int positiveNumber) {
        this.positiveNumber += positiveNumber;
    }

    public void addTotalNumber(int totalNumber) {
        this.totalNumber += totalNumber;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }
}
