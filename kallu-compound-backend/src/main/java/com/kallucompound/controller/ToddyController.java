package com.kallucompound.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kallucompound.entity.Toddy;
import com.kallucompound.repository.ToddyRepository;

@RestController
public class ToddyController {

    private final ToddyRepository toddyRepository;

    public ToddyController(ToddyRepository toddyRepository) {
        this.toddyRepository = toddyRepository;
    }

    // GET ALL TODDY
    @GetMapping("/api/toddy")
    public List<Toddy> getAllToddy() {

        return toddyRepository.findAll();
    }

    // UPDATE PRICE
    @PutMapping("/api/toddy/{id}")
    public Toddy updatePrice(
            @PathVariable Long id,
            @RequestParam double price) {

        Toddy toddy =
                toddyRepository
                        .findById(id)
                        .orElseThrow();

        toddy.setPrice(price);

        return toddyRepository.save(toddy);
    }

    // UPDATE AVAILABILITY
    @PutMapping("/api/toddy/{id}/availability")
    public Toddy updateAvailability(
            @PathVariable Long id,
            @RequestParam boolean available) {

        Toddy toddy =
                toddyRepository
                        .findById(id)
                        .orElseThrow();

        toddy.setAvailable(available);

        return toddyRepository.save(toddy);
    }
}