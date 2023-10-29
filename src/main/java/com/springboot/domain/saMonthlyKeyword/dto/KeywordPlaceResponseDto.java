package com.springboot.domain.saMonthlyKeyword.dto;
import com.springboot.domain.saMonthlyKeyword.entity.SaMonthlyKeyword;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KeywordPlaceResponseDto {

    private long samonthlykeyword_id;
    private int familyCount = 0;

    private int coupleCount = 0;

    private int friendCount = 0;

    private int kidsCount = 0;

    private int aloneCount = 0;

    private int healingCount = 0;

    private int walkCount = 0;

    private int picnicCount = 0;

    private int hikingCount = 0;

    private int beachCount = 0;

    private int flowerCount = 0;

    private int tourCount = 0;

    private int shoppingCount = 0;

    private int museumCount = 0;

    private int foodCount = 0;

    private int nightViewCount = 0;

    private int exerciseCount = 0;

    @Builder
    public KeywordPlaceResponseDto(SaMonthlyKeyword entity) {
        this.samonthlykeyword_id = entity.getId();
        this.familyCount = entity.getFamilyCount();
        this.coupleCount = entity.getCoupleCount();
        this.friendCount = entity.getFriendCount();
        this.aloneCount = entity.getAloneCount();
        this.walkCount = entity.getWalkCount();
        this.shoppingCount = entity.getShoppingCount();
        this.beachCount = entity.getBeachCount();
        this.hikingCount = entity.getHikingCount();
        this.museumCount = entity.getMuseumCount();
        this.picnicCount = entity.getPicnicCount();
        this.foodCount = entity.getFoodCount();
        this.healingCount = entity.getHealingCount();
        this.nightViewCount = entity.getNightViewCount();
        this.exerciseCount = entity.getExerciseCount();
        this.kidsCount = entity.getKidsCount();
        this.flowerCount = entity.getFlowerCount();
    }

    public void setSamonthlykeyword_id(long id){this.samonthlykeyword_id = id;}
    public void addFamilyCount(int count) {
        this.familyCount += count;
    }

    public void addCoupleCount(int count) { this.coupleCount += count;}

    public void addFriendCount(int count) {this.friendCount += count;}

    public void addKidsCount(int count) {this.kidsCount += count;}

    public void addAloneCount(int count) {this.aloneCount += count;}

    public void addHealingCount(int count) {this.healingCount += count;}

    public void addWalkCount(int count) {this.walkCount += count;}

    public void addPicnicCount(int count) {this.picnicCount += count;}

    public void addHikingCount(int count) {this.hikingCount += count;}

    public void addBeachCount(int count) {this.beachCount += count;}

    public void addFlowerCount(int count) {this.flowerCount += count;}

    public void addTourCount(int count) {this.tourCount += count;}

    public void addShoppingCount(int count) {this.shoppingCount += count;}

    public void addMuseumCount(int count) {this.museumCount += count;}

    public void addFoodCount(int count) {this.foodCount += count;}

    public void addNightViewCount(int count) {this.nightViewCount += count;}

    public void addExerciseCount(int count) {this.exerciseCount += count;}
}
