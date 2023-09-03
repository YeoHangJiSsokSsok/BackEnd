package com.springboot.domain.saMonthlySummary.entity;

import com.springboot.domain.place.entity.Places;
import com.springboot.domain.review.entity.Reviews;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class SaMonthlySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @Column(nullable = false)
    private int moodPositiveNumber;

    @Column(nullable = false)
    private int moodNegativeNumber;

    @Column(nullable = false)
    private int moodNeutralNumber;

    @Column(nullable = false)
    private int transportPositiveNumber;

    @Column(nullable = false)
    private int transportNegativeNumber;

    @Column(nullable = false)
    private int transportNeutralNumber;

    @Column(nullable = false)
    private int congestionPositiveNumber;

    @Column(nullable = false)
    private int congestionNegativeNumber;

    @Column(nullable = false)
    private int congestionNeutralNumber;

    @Column(nullable = false)
    private int infraPositiveNumber;

    @Column(nullable = false)
    private int infraNegativeNumber;

    @Column(nullable = false)
    private int infraNeutralNumber;

    @ManyToOne
    @JoinColumn(name = "PLACE_ID")
    private Places place;

    @OneToMany(mappedBy = "saMonthlySummary")
    List<Reviews> reviews = new ArrayList<>();


    @Builder
    public SaMonthlySummary(Places place, int moodPositiveNumber, int moodNegativeNumber, int moodNeutralNumber , int transportPositiveNumber, int transportNegativeNumber, int transportNeutralNumber, int congestionPositiveNumber, int congestionNegativeNumber, int congestionNeutralNumber, int infraPositiveNumber, int infraNegativeNumber, int infraNeutralNumber) {
        this.place = place;
        this.moodPositiveNumber = moodPositiveNumber;
        this.moodNegativeNumber = moodNegativeNumber;
        this.moodNeutralNumber = moodNeutralNumber;
        this.transportPositiveNumber = transportPositiveNumber;
        this.transportNegativeNumber = transportNegativeNumber;
        this.transportNeutralNumber = transportNeutralNumber;
        this.congestionPositiveNumber = congestionPositiveNumber;
        this.congestionNegativeNumber = congestionNegativeNumber;
        this.congestionNeutralNumber = congestionNeutralNumber;
        this.infraPositiveNumber = infraPositiveNumber;
        this.infraNegativeNumber = infraNegativeNumber;
        this.infraNeutralNumber = infraNeutralNumber;
    }
}
