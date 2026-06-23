package com.society.management.controller;

import com.society.management.entity.Visitor;
import com.society.management.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VisitorController {

    @Autowired
    private VisitorRepository visitorRepository;

    @GetMapping("/visitors")
    public List<Visitor> getVisitors() {
        return visitorRepository.findAll();
    }

    @PostMapping("/visitors")
    public Visitor addVisitor(@RequestBody Visitor visitor) {
        return visitorRepository.save(visitor);
    }
}