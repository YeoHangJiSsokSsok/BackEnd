package com.springboot.domain.place.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceRequestDto {
    private String name;
    @Builder
    public PlaceRequestDto(String name) {
        this.name = name;
    }
}
