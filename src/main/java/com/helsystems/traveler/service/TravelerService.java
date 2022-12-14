package com.helsystems.traveler.service;

import com.helsystems.traveler.dto.TravelerDto;

import java.util.List;

public interface TravelerService extends IGenericService<TravelerDto> {
    List<TravelerDto> findTravelersByRide(Long rideId);

}
