package com.society.management.controller;

import com.society.management.entity.Flat;
import com.society.management.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FlatController {

    @Autowired
    private FlatRepository flatRepository;

    @GetMapping("/flats")
    public List<Flat> getAllFlats() {
        return flatRepository.findAll();
    }
}