package com.springboot.domain.saMonthlySummary.entity;

import com.springboot.domain.place.entity.Places;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaMonthlySummaryRepository extends JpaRepository<SaMonthlySummary, Long> {
    List<SaMonthlySummary> findByPlaceAndMonth(Places place, int month);

    List<SaMonthlySummary> findByPlace(Places place);
}
