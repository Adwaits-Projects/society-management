package com.society.management.controller;

import com.society.management.entity.Amenity;
import com.society.management.repository.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AmenityController {

    @Autowired
    private AmenityRepository amenityRepository;

    @GetMapping("/amenities")
    public List<Amenity> getAllAmenities() {
        return amenityRepository.findAll();
    }
}

