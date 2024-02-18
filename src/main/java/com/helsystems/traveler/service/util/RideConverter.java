package com.helsystems.traveler.service.util;

import com.helsystems.traveler.dao.TravelerRepository;
import com.helsystems.traveler.dto.RideDto;
import com.helsystems.traveler.model.Ride;
import com.helsystems.traveler.model.enums.Direction;
import com.helsystems.traveler.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RideConverter {

    @Autowired
    TravelerConverter travelerConverter;

    @Autowired
    TravelerRepository travelerDao;
    public RideDto convertRideToDto(Ride ride){
        RideDto rideDto = new RideDto();
        rideDto.setId(ride.getId());
        rideDto.setStatus(ride.getStatus().name());
        rideDto.setDate(ride.getDate());
        rideDto.setPrice(ride.getPrice());
        rideDto.setDirection(ride.getDirection().name());
        rideDto.setDriver(travelerConverter.convertTravelerToDto(ride.getDriver()));
        rideDto.setCapacity(ride.getCapacity());
        rideDto.setDescription(ride.getDescription());
        rideDto.setCurrentNumberOfPassengers(ride.getCurrentNumberOfPassengers());

        return rideDto;
    }

    public Ride convertDtoToRide(Ride ride, RideDto rideDto){

        switch(rideDto.getStatus()) {
            case "AVAILABLE": ride.setStatus(Status.AVAILABLE);
                break;
            case "FULL": ride.setStatus(Status.FULL);
                break;
            case "CANCELLED": ride.setStatus(Status.CANCELLED);
                break;
            case "COMPLETED": ride.setStatus(Status.COMPLETED);

            default: throw new IllegalArgumentException("illegal status");
        }

        switch (rideDto.getDirection()) {
            case "FIN" -> ride.setDirection(Direction.FIN);
            case "RUS" -> ride.setDirection(Direction.RUS);
            default -> throw new IllegalArgumentException("illegal direction");
        }
        ride.setCapacity(rideDto.getCapacity());
        ride.setDate(rideDto.getDate());
        ride.setDriver(travelerDao.findTravelerByTgUsername(rideDto.getDriver().getTgUsername()));
        ride.setPrice(rideDto.getPrice());
        ride.setDescription(rideDto.getDescription());

        return ride;
    }
}
