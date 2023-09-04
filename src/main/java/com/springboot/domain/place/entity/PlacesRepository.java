package com.springboot.domain.place.entity;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlacesRepository extends JpaRepository<Places, Long> {
    public List<Places> findByRegion(String name);
    public List<Places> findByNameContaining(String name);
}
