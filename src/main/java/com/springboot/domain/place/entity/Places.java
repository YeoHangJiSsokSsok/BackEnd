package com.springboot.domain.place.entity;

import com.springboot.domain.saMonthlySummary.entity.SaMonthlySummary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String region;

    @Column(nullable = false)
    private String name;

    @Column
    private String address;

    @OneToMany(mappedBy = "place")
    List<SaMonthlySummary> saMonthlySummaries = new ArrayList<>();

    @Builder
    public Places(String region, String name, String address) {
        this.region = region;
        this.name = name;
        this.address = address;
    }
}
