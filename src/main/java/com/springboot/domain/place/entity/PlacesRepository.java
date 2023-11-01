package com.springboot.domain.place.entity;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Places, Long> {
    public List<Places> findByRegion(String name);
    @Query("SELECT p FROM Places p WHERE p.name LIKE %:name% OR p.region LIKE %:name%")
    List<Places> findByNameOrRegion(@Param("name") String name);

}
