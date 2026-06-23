package com.society.management.controller;

import com.society.management.entity.Wing;
import com.society.management.repository.WingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WingController {

    @Autowired
    private WingRepository wingRepository;

    @GetMapping("/wings")
    public List<Wing> getWings() {
        return wingRepository.findAll();
    }
}