package com.springboot.domain.place.dto;

import com.springboot.domain.place.entity.Places;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceResponseDto {

    private Long id;

    private String region;

    private String name;

    @Builder
    public PlaceResponseDto(Places entity) {
        this.region = entity.getRegion();
        this.name = entity.getName();
    }

}
