package com.society.management.controller;

import com.society.management.entity.Floor;
import com.society.management.repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FloorController {

    @Autowired
    private FloorRepository floorRepository;

    @GetMapping("/floors/{wingId}")
    public List<Floor> getFloors(@PathVariable Integer wingId) {
        return floorRepository.findByWingId(wingId);
    }
}