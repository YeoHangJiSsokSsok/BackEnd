package com.springboot.domain.saMonthlyKeyword.entity;

import com.springboot.domain.place.entity.Places;
import com.springboot.domain.review.entity.Reviews;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class SaMonthlyKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int familyCount;

    @Column(nullable = false)
    private int coupleCount;

    @Column(nullable = false)
    private int friendCount;

    @Column(nullable = false)
    private int kidsCount;

    @Column(nullable = false)
    private int aloneCount;

    @Column(nullable = false)
    private int healingCount;

    @Column(nullable = false)
    private int walkCount;

    @Column(nullable = false)
    private int picnicCount;

    @Column(nullable = false)
    private int hikingCount;

    @Column(nullable = false)
    private int beachCount;

    @Column(nullable = false)
    private int flowerCount;

    @Column(nullable = false)
    private int tourCount;

    @Column(nullable = false)
    private int shoppingCount;

    @Column(nullable = false)
    private int museumCount;

    @Column(nullable = false)
    private int foodCount;

    @Column(nullable = false)
    private int nightViewCount;

    @Column(nullable = false)
    private int exerciseCount;

    @ManyToOne
    @JoinColumn(name = "PLACE_ID")
    private Places place;

    @OneToMany(mappedBy = "saMonthlyKeyword")
    List<Reviews> reviews = new ArrayList<>();

}
