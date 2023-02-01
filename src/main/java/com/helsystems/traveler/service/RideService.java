package com.helsystems.traveler.service;

import com.helsystems.traveler.dto.RideDto;
import com.helsystems.traveler.model.Ride;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RideService extends IGenericService<RideDto> {

     Ride convertDtoToRide(Ride ride, RideDto rideDto);
     RideDto convertRideToDto(Ride ride);


     List<RideDto> findAllByParams(Integer capacity, String direction, LocalDate dateAfter, LocalDate dateBefore, String status);

     void addPassengerToRide(Long rideDto, Long rideId);
}
