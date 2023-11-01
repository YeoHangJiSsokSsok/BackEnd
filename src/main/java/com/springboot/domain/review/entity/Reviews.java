package com.springboot.domain.review.entity;

import com.springboot.domain.saMonthlyKeyword.entity.SaMonthlyKeyword;
import com.springboot.domain.saMonthlySummary.entity.SaMonthlySummary;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SAMONTHLYSUMMARY_ID")
    private SaMonthlySummary saMonthlySummary;

    @ManyToOne
    @JoinColumn(name = "SAMONTHLYKEYWORD_ID")
    private SaMonthlyKeyword saMonthlyKeyword;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int mood;

    @Column(nullable = false)
    private int transport;

    @Column(nullable = false)
    private int congestion;

    @Column(nullable = false)
    private int infra;

    @Column
    private String keyword;


    @Builder
    public Reviews(SaMonthlySummary saMonthlySummary, String date, String content, int mood, int transport, int congestion, int infra, String keyword) {
        this.saMonthlySummary = saMonthlySummary;
        this.date = date;
        this.content = content;
        this.mood = mood;
        this.transport = transport;
        this.congestion = congestion;
        this.infra = infra;
        this.keyword = keyword;
    }
}
