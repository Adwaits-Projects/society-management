package com.society.management.controller;

import com.society.management.entity.Flat;
import com.society.management.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FlatByFloorController {

    @Autowired
    private FlatRepository flatRepository;

    @GetMapping("/flats/{floorId}")
    public List<Flat> getFlats(@PathVariable Integer floorId) {
        return flatRepository.findByFloorId(floorId);
    }
}