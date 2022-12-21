package com.helsystems.traveler.controller;

import com.helsystems.traveler.dto.TravelerDto;
import com.helsystems.traveler.service.TravelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TravelerController {

    @Autowired
    TravelerService travelerService;
    @GetMapping("/travelers")
    private List<TravelerDto> getAllUsers()
    {
        return travelerService.findAll();
    }
    @GetMapping("/travelers/{travelerId}")
    private TravelerDto getUserById(@PathVariable("travelerId") long travelerId) {return travelerService.findById(travelerId);}
    @DeleteMapping("/travelers/{travelerId}")
    private void deleteUser(@PathVariable("travelerId") long travelerId)
    {
        travelerService.deleteById(travelerId);
    }
    @PostMapping("/travelers")
    private TravelerDto saveUser(@RequestBody TravelerDto travelerDto)
    {
        return travelerService.save(travelerDto);
    }
    @PutMapping("/travelers")
    private TravelerDto update(@RequestBody TravelerDto travelerDto)
    {
        return travelerService.save(travelerDto);
    }


}

// additional CRUD methods

