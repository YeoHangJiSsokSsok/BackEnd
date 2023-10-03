package com.springboot.domain.saMonthlySummary.service;

import com.springboot.domain.place.entity.Places;
import com.springboot.domain.place.entity.PlacesRepository;
import com.springboot.domain.saMonthlySummary.dto.SaCategorySummaryResponseDto;
import com.springboot.domain.saMonthlySummary.dto.SaPlaceResponseDto;
import com.springboot.domain.saMonthlySummary.entity.SaMonthlySummary;
import com.springboot.domain.saMonthlySummary.entity.SaMonthlySummaryRepository;
import com.springboot.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.*;
@RequiredArgsConstructor
@Service
public class SaMonthlySummaryService {

    private final SaMonthlySummaryRepository saRepository;
    private final PlacesRepository placesRepository;

    @Transactional(readOnly = true)
    public List<SaCategorySummaryResponseDto> getCategoryBestSAPlace(String code) {

        List<Places> places = placesRepository.findAll();
        if (places.isEmpty()) {
            throw new EntityNotFoundException(PLACE_NOT_FOUND, "장소 리스트가 없습니다." );
        }

        List<SaMonthlySummary> saMonthlySummaries = saRepository.findAll();
        if (saMonthlySummaries.isEmpty()) {
            throw new EntityNotFoundException(MONTHLY_SUMMARY_NOT_FOUND, "해당 코드 월 별 summary 리스트가 없습니다 : " + code );
        }

        return getSaCategorySummaryResponseDtos(code, places, saMonthlySummaries);
    }

    public List<SaCategorySummaryResponseDto> getMonthlyCategoryBestSAPlace(String code, int month) {

        List<Places> places = placesRepository.findAll();
        if (places.isEmpty()) {
            throw new EntityNotFoundException(PLACE_NOT_FOUND, "장소 리스트가 없습니다." );
        }

        List<SaMonthlySummary> saMonthlySummaries = saRepository.findByMonth(month);
        if (saMonthlySummaries.isEmpty()) {
            throw new EntityNotFoundException(MONTHLY_SUMMARY_NOT_FOUND, "해당 코드 월 별 summary 리스트가 없습니다 : " + code);
        }

        return getSaCategorySummaryResponseDtos(code, places, saMonthlySummaries);
    }

    private List<SaCategorySummaryResponseDto> getSaCategorySummaryResponseDtos(String code, List<Places> places, List<SaMonthlySummary> saMonthlySummaries) {

        List<SaCategorySummaryResponseDto> list = places.stream()
                .map(entity -> SaCategorySummaryResponseDto.builder()
                        .place(entity)
                        .build())
                .collect(Collectors.toList());

        switch (code) {
            case "C001":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlySummaries.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addPositiveNumber(entity.getMoodPositiveNumber());
                                list.get(index).addTotalNumber(entity.getMoodPositiveNumber() + entity.getMoodNegativeNumber() + entity.getMoodNeutralNumber());
                            });
                }
                break;
            case "C002":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlySummaries.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addPositiveNumber(entity.getTransportPositiveNumber());
                                list.get(index).addTotalNumber(entity.getTransportPositiveNumber() + entity.getTransportNegativeNumber() + entity.getTransportNeutralNumber());
                            });
                }
                break;
            case "C003":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlySummaries.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addPositiveNumber(entity.getCongestionPositiveNumber());
                                list.get(index).addTotalNumber(entity.getCongestionPositiveNumber() + entity.getCongestionNegativeNumber() + entity.getCongestionNeutralNumber());
                            });
                }
                break;
            case "C004":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlySummaries.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addPositiveNumber(entity.getInfraPositiveNumber());
                                list.get(index).addTotalNumber(entity.getInfraPositiveNumber() + entity.getInfraNegativeNumber() + entity.getInfraNeutralNumber());
                            });
                }
                break;
            default:
                throw new EntityNotFoundException(CATEGORY_NOT_FOUND, "해당 코드의 카테고리가 없습니다 : " + code);
        }
        list.stream()
                .forEach(entity -> {
                        if(entity.getPositiveNumber() == 0 && entity.getTotalNumber() == 0)
                            entity.setProportion(0);
                        else
                            entity.setProportion((double) entity.getPositiveNumber()/ (double) entity.getTotalNumber());
                });

        List<SaCategorySummaryResponseDto> sortedList = list.stream().sorted(Comparator.comparing(SaCategorySummaryResponseDto::getProportion, Comparator.reverseOrder())
                        .thenComparing(SaCategorySummaryResponseDto::getPositiveNumber, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return sortedList;
    }


    @Transactional(readOnly = true)
    public List<SaPlaceResponseDto> getPlace(long placeId) {

        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException(PLACE_NOT_FOUND, "해당 id의 장소가 없습니다." + placeId));

        List<SaMonthlySummary> saMonthlySummaries = saRepository.findByPlace(place);

        if (saMonthlySummaries.isEmpty()) {
            throw new EntityNotFoundException(MONTHLY_SUMMARY_NOT_FOUND, "summary 리스트가 없습니다." );
        }

        return getSaPlaceResponseDtosByPlace(saMonthlySummaries);
    }


    @Transactional(readOnly = true)
    public List<SaPlaceResponseDto> getMonthlyPlace(long placeId, int month) {
        if (month < 1 || month > 12) {
            throw new EntityNotFoundException(MONTH_NOT_EXIST, "입력한 월은 존재하지 않습니다 : " + month );
        }
        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException(PLACE_NOT_FOUND, "해당 id의 장소가 없습니다 : " + placeId));

        List<SaMonthlySummary> saMonthlySummaries = saRepository.findByPlaceAndMonth(place, month);

        if (saMonthlySummaries.isEmpty()) {
            throw new EntityNotFoundException(MONTHLY_SUMMARY_NOT_FOUND, "해당 월 summary가 없습니다 : " + month );
        }

        return getSaPlaceResponseDtosByPlace(saMonthlySummaries);
    }
    
    private List<SaPlaceResponseDto> getSaPlaceResponseDtosByPlace(List<SaMonthlySummary> saMonthlySummaries) {
        List<SaPlaceResponseDto> list = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            list.add(SaPlaceResponseDto.builder()
                    .category("C00"+i)
                    .samonthlysummary_id(saMonthlySummaries.get(0).getId())
                    .build());
        }
        saMonthlySummaries.stream()
                .forEach(entity -> {
                    list.get(0).addPositive(entity.getMoodPositiveNumber());
                    list.get(1).addPositive(entity.getTransportPositiveNumber());
                    list.get(2).addPositive(entity.getCongestionPositiveNumber());
                    list.get(3).addPositive(entity.getInfraPositiveNumber());

                    list.get(0).addNegative(entity.getMoodNegativeNumber());
                    list.get(1).addNegative(entity.getTransportNegativeNumber());
                    list.get(2).addNegative(entity.getCongestionNegativeNumber());
                    list.get(3).addNegative(entity.getInfraNegativeNumber());

                    list.get(0).addNeutral(entity.getMoodNeutralNumber());
                    list.get(1).addNeutral(entity.getTransportNeutralNumber());
                    list.get(2).addNeutral(entity.getCongestionNeutralNumber());
                    list.get(3).addNeutral(entity.getInfraNeutralNumber());
                });
        return list;
    }

}
