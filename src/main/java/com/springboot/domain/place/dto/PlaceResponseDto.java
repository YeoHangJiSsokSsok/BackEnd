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

    private String address;

    @Builder
    public PlaceResponseDto(Places entity) {
        this.id = entity.getId();
        this.region = entity.getRegion();
        this.name = entity.getName();
        this.address = entity.getAddress();
    }

}
