package com.society.management.repository;

import com.society.management.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FloorRepository extends JpaRepository<Floor, Integer> {

    List<Floor> findByWingId(Integer wingId);

}