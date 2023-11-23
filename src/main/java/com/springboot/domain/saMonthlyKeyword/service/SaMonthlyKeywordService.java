package com.springboot.domain.saMonthlyKeyword.service;

import com.springboot.domain.place.entity.Places;
import com.springboot.domain.place.entity.PlacesRepository;
import com.springboot.domain.saMonthlyKeyword.dto.KeywordListResponseDto;
import com.springboot.domain.saMonthlyKeyword.dto.KeywordPlaceResponseDto;
import com.springboot.domain.saMonthlyKeyword.entity.SaMonthlyKeyword;
import com.springboot.domain.saMonthlyKeyword.entity.SaMonthlyKeywordRepository;
import com.springboot.domain.saMonthlySummary.entity.SaMonthlySummary;
import com.springboot.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.global.error.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class SaMonthlyKeywordService {

    private final SaMonthlyKeywordRepository saRepository;
    private final PlacesRepository placesRepository;

    @Transactional(readOnly = true)
    public List<KeywordListResponseDto> getKeywordBestPlace(String keyword) {

        List<Places> places = placesRepository.findAll();
        if (places.isEmpty()) {
            throw new EntityNotFoundException(PLACE_NOT_FOUND, "장소 리스트가 없습니다.");
        }

        List<SaMonthlyKeyword> saMonthlyKeywords = saRepository.findAll();
        if (saMonthlyKeywords.isEmpty()) {
            throw new EntityNotFoundException(KEYWORD_LIST_NOT_FOUND, "해당 키워드 리스트가 없습니다 : " + keyword);
        }

        return getSaCategorySummaryResponseDtos(keyword, places, saMonthlyKeywords);
    }

    @Transactional(readOnly = true)
    public List<KeywordListResponseDto> getMonthlyKeywordBestPlace(String keyword, int month) {

        List<Places> places = placesRepository.findAll();
        if (places.isEmpty()) {
            throw new EntityNotFoundException(PLACE_NOT_FOUND, "장소 리스트가 없습니다.");
        }

        List<SaMonthlyKeyword> saMonthlyKeywords = saRepository.findByMonth(month);
        if (saMonthlyKeywords.isEmpty()) {
            throw new EntityNotFoundException(KEYWORD_LIST_NOT_FOUND, "해당 월에 대한 카워드 리스트가 없습니다 : " + + month + "월 " +keyword);
        }

        return getSaCategorySummaryResponseDtos(keyword, places, saMonthlyKeywords);
    }

    private List<KeywordListResponseDto> getSaCategorySummaryResponseDtos(String keyword, List<Places> places, List<SaMonthlyKeyword> saMonthlyKeywords) {

        List<KeywordListResponseDto> list = places.stream()
                .map(entity -> KeywordListResponseDto.builder()
                        .place(entity)
                        .build())
                .collect(Collectors.toList());

        switch (keyword) {
            case "가족":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getFamilyCount());
                            });
                }
                break;
            case "연인":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getCoupleCount());
                            });
                }
                break;
            case "친구":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getFriendCount());
                            });
                }
                break;
            case "아이":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getKidsCount());
                            });
                }
                break;
            case "홀로":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getAloneCount());
                            });
                }
                break;
            case "힐링":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getHealingCount());
                            });
                }
                break;
            case "산책":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getWalkCount());
                            });
                }
                break;
            case "나들이":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getPicnicCount());
                            });
                }
                break;
            case "등산":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getHikingCount());
                            });
                }
                break;
            case "바다":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getBeachCount());
                            });
                }
                break;
            case "꽃":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getFlowerCount());
                            });
                }
                break;
            case "관광":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getTourCount());
                            });
                }
                break;
            case "쇼핑":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getShoppingCount());
                            });
                }
                break;
            case "전시":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getMuseumCount());
                            });
                }
                break;
            case "맛집":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getFoodCount());
                            });
                }
                break;
            case "야경":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getNightViewCount());
                            });
                }
                break;
            case "운동":
                for (int i = 0; i < list.size(); i++) {
                    int index = i;
                    saMonthlyKeywords.stream()
                            .filter(s -> s.getPlace().getId() == index + 1)
                            .forEach(entity -> {
                                list.get(index).addKeywordCount(entity.getExerciseCount());
                            });
                }
                break;
            default:
                throw new EntityNotFoundException(KEYWORD_NOT_FOUND, "해당 키워드가 없습니다 : " + keyword);
        }

        List<KeywordListResponseDto> sortedList = list.stream().sorted(Comparator.comparing(KeywordListResponseDto::getKeywordCount, Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return sortedList;
    }


    @Transactional(readOnly = true)
    public KeywordPlaceResponseDto getPlace(long placeId) {

        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException(PLACE_NOT_FOUND, "해당 id의 장소가 없습니다." + placeId));

        List<SaMonthlyKeyword> saMonthlyKeywords = saRepository.findByPlace(place);

        if (saMonthlyKeywords.isEmpty()) {
            throw new EntityNotFoundException(KEYWORD_LIST_NOT_FOUND, "해당 장소에 대한 카워드 리스트가 없습니다 : "+ place.getName());
        }

        KeywordPlaceResponseDto responseDto = new KeywordPlaceResponseDto();
        responseDto.setSamonthlykeyword_id(saMonthlyKeywords.get(0).getId());

        saMonthlyKeywords.stream()
                .forEach(entity -> {
                    responseDto.addFamilyCount(entity.getFamilyCount());
                    responseDto.addCoupleCount(entity.getCoupleCount());
                    responseDto.addKidsCount(entity.getKidsCount());
                    responseDto.addFlowerCount(entity.getFlowerCount());
                    responseDto.addFoodCount(entity.getFoodCount());
                    responseDto.addFriendCount(entity.getFriendCount());
                    responseDto.addHealingCount(entity.getHealingCount());
                    responseDto.addShoppingCount(entity.getShoppingCount());
                    responseDto.addMuseumCount(entity.getMuseumCount());
                    responseDto.addHikingCount(entity.getHikingCount());
                    responseDto.addPicnicCount(entity.getPicnicCount());
                    responseDto.addTourCount(entity.getTourCount());
                    responseDto.addWalkCount(entity.getWalkCount());
                    responseDto.addExerciseCount(entity.getExerciseCount());
                    responseDto.addBeachCount(entity.getBeachCount());
                    responseDto.addAloneCount(entity.getAloneCount());
                    responseDto.addNightViewCount(entity.getNightViewCount());
                });

        return responseDto;
    }

    @Transactional(readOnly = true)
    public KeywordPlaceResponseDto getMonthlyPlace(long placeId, int month) {
        if (month < 1 || month > 12) {
            throw new EntityNotFoundException(MONTH_NOT_EXIST, "입력한 월은 존재하지 않습니다 : " + month);
        }
        Places place = placesRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException(PLACE_NOT_FOUND, "해당 id의 장소가 없습니다 : " + placeId));

        List<SaMonthlyKeyword> saMonthlyKeywords = saRepository.findByPlaceAndMonth(place, month);

        if (saMonthlyKeywords.isEmpty()) {
            throw new EntityNotFoundException(KEYWORD_LIST_NOT_FOUND, "해당 장소와 월에 대한 카워드 리스트가 없습니다 : " + + month + "월");
        }

        KeywordPlaceResponseDto keywordPlaceResponseDto = KeywordPlaceResponseDto.builder()
                .entity(saMonthlyKeywords.get(0)).build();

        return keywordPlaceResponseDto ;
    }

}
