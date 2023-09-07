package com.springboot.domain.saMonthlySummary.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaPlaceResponseDto {

    private long samonthlysummary_id;
    private String category;

    private int positive = 0;

    private int negative = 0;

    private int neutral = 0;

    @Builder
    public SaPlaceResponseDto(long samonthlysummary_id, String category) {
        this.samonthlysummary_id = samonthlysummary_id;
        this.category = category;
    }

    public void addPositive(int positive) {
        this.positive += positive;
    }

    public void addNegative(int negative) {
        this.negative += negative;
    }

    public void addNeutral(int neutral) {
        this.neutral += neutral;
    }
}
