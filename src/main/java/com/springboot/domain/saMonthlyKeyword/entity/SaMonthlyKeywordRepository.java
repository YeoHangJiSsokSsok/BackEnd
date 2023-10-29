package com.springboot.domain.saMonthlyKeyword.entity;

import com.springboot.domain.place.entity.Places;
import com.springboot.domain.saMonthlySummary.entity.SaMonthlySummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaMonthlyKeywordRepository extends JpaRepository<SaMonthlyKeyword, Long> {
    List<SaMonthlyKeyword> findByPlaceAndMonth(Places place, int month);

    List<SaMonthlyKeyword> findByPlace(Places place);

    List<SaMonthlyKeyword> findByMonth(int month);
}
