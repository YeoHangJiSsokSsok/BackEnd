package com.springboot.domain.place.service;

import com.springboot.domain.place.dto.PlaceRequestDto;
import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.place.entity.Places;
import com.springboot.domain.place.entity.PlacesRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.springboot.global.error.exception.ErrorCode.PLACE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class PlaceService {

    private  final PlacesRepository placesRepository;

    @Transactional
    public PlaceResponseDto getPlace(Long place_id) {
        Places entity = placesRepository.findById(place_id)
                .orElseThrow(() -> new EntityNotFoundException(PLACE_NOT_FOUND, "해당 place가 존재하지 않습니다." + place_id));
        return new PlaceResponseDto(entity);
    }

//    public PlaceResponseDto getPlaceByName(PlaceRequestDto placeRequestDto) {
//
//    }
}
