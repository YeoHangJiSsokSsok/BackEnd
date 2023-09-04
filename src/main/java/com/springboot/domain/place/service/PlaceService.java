package com.springboot.domain.place.service;

import com.springboot.domain.place.dto.PlaceRequestDto;
import com.springboot.domain.place.dto.PlaceResponseDto;
import com.springboot.domain.place.entity.Places;
import com.springboot.domain.place.entity.PlacesRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.CATEGORYLIST_NOT_FOUND;
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

    @Transactional
    public List<PlaceResponseDto> getAllPlace() {
        List<Places> placesList = placesRepository.findAll();
        List<PlaceResponseDto> list = placesList.stream()
                .map(entity -> PlaceResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new EntityNotFoundException(PLACE_NOT_FOUND, "place가 존재하지 않습니다." );
        }
        return list;
    }

    @Transactional
    public List<PlaceResponseDto> getPlaceByRegion(PlaceRequestDto placeRequestDto) {
        List<Places> placesList = placesRepository.findByRegion(placeRequestDto.getName());
        System.out.println(placesList.size());
        List<PlaceResponseDto> list = placesList.stream()
                .map(entity -> PlaceResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new EntityNotFoundException(PLACE_NOT_FOUND, "place가 존재하지 않습니다." );
        }
        return list;
    }

    @Transactional
    public List<PlaceResponseDto> getPlaceByName(PlaceRequestDto placeRequestDto) {
        List<Places> placesList = placesRepository.findByNameContaining(placeRequestDto.getName());
        List<PlaceResponseDto> list = placesList.stream()
                .map(entity -> PlaceResponseDto.builder()
                        .entity(entity)
                        .build())
                .collect(Collectors.toList());

        if (list.isEmpty()) {
            throw new EntityNotFoundException(PLACE_NOT_FOUND, "place가 존재하지 않습니다." );
        }
        return list;
    }
}
