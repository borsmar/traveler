package com.helsystems.traveler.controller;

import com.helsystems.traveler.dto.RideDto;
import com.helsystems.traveler.dto.TravelerDto;
import com.helsystems.traveler.service.RideService;
import com.helsystems.traveler.service.TravelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@CrossOrigin
public class RideController {
    @Autowired
    RideService rideService;
    @Autowired
    TravelerService travelerService;
    @GetMapping("/rides")
    private List<RideDto> getAllRides()
    {
        return rideService.findAll();
    }

    @GetMapping("/rides/query")
    private List<RideDto> getRidesByParams(@RequestParam(required = false)  Integer capacity,
                                           @RequestParam(required = false)  String direction,
                                           @RequestParam(required = false) LocalDate dateAfter,
                                           @RequestParam(required = false) LocalDate dateBefore,
                                           @RequestParam(required = false) String status)
    {
        return rideService.findAllByParams(capacity, direction, dateAfter, dateBefore, status);
    }

    @GetMapping("/rides/{rideId}/travelers-list")
    private List<TravelerDto> getTravelersOfRides(@PathVariable("rideId") long rideId)
    {
        return travelerService.findTravelersByRide(rideId);
    }
    @GetMapping("/rides/{rideId}")
    private RideDto getRideById(@PathVariable("rideId") long rideId)
    {
        return rideService.findById(rideId);
    }
    @DeleteMapping("/rides/{rideId}")
    private void deleteRide(@PathVariable("rideId") long rideId)
    {
        rideService.deleteById(rideId);
    }
    @PostMapping("/rides")
    private RideDto saveRide(@RequestBody RideDto rideDto)
    {
        return rideService.save(rideDto);
    }
    @PutMapping("/rides/{rideId}")
    private RideDto update(@PathVariable("rideId") long rideId, @RequestBody RideDto rideDto)
    {
        return rideService.update(rideDto, rideId);
    }

    @PutMapping("/rides/add-passenger/{passengerId}/to-ride/{rideId}")
    private void addPassengerToRide(@PathVariable("passengerId") long passengerId, @PathVariable("rideId") long rideId)
    {
         rideService.addPassengerToRide(passengerId, rideId);
    }

    @DeleteMapping("/rides/delete-passenger/{passengerId}/from-ride/{rideId}")
    private void deletePassengerFromRide(@PathVariable("passengerId") long passengerId, @PathVariable("rideId") long rideId)
    {
        rideService.addPassengerToRide(passengerId, rideId);
    }
}
