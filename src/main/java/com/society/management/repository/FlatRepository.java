package com.society.management.repository;

import com.society.management.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlatRepository extends JpaRepository<Flat, Integer> {

    List<Flat> findByFloorId(Integer floorId);

}