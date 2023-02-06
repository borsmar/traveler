package com.helsystems.traveler.service;

import com.helsystems.traveler.dao.RideRepository;
import com.helsystems.traveler.dao.TravelerRepository;
import com.helsystems.traveler.dto.RideDto;
import com.helsystems.traveler.dto.TravelerDto;
import com.helsystems.traveler.model.Ride;
import com.helsystems.traveler.model.Traveler;
import com.helsystems.traveler.model.enums.Direction;
import com.helsystems.traveler.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.helsystems.traveler.dao.RideSpecs.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class RideServiceImpl implements RideService {
    @Autowired
    private RideRepository dao;

    @Autowired
    private TravelerRepository travelerDao;


    @Override
    public List<RideDto> findAll() {
        List<Ride> rideList = (List<Ride>) dao.findAll();
        return rideList.stream().map(this::convertRideToDto).collect(Collectors.toList());
    }


   @Override
    public List<RideDto> findAllByParams(Integer capacity,
                                         String direction,
                                         LocalDate dateAfter,
                                         LocalDate dateBefore,
                                         String status) {

        List<Ride> rideList = dao.findAll
        (
                 where(hasCapacity(capacity))
                .and(hasDirection(direction))
                .and(isBetweenDateAndTime(dateBefore,dateAfter)
                .and(hasStatus(status)))
        );
        return rideList.stream().map(this::convertRideToDto).collect(Collectors.toList());
    }


    @Override
    public RideDto save(RideDto rideDto) {
        dao.save(convertDtoToRide(new Ride(), rideDto));
        return rideDto;
    }

    @Override
    public RideDto update(RideDto rideDto, Long rideId) {
        Ride ride = convertDtoToRide(dao.findById(rideId).get(), rideDto);
        return convertRideToDto(dao.save(ride));
    }

    @Override
    public void addPassengerToRide(Long passengerId, Long rideId) {
        Traveler traveler = travelerDao.findById(passengerId).get();
        traveler.getRides().add(dao.findById(rideId).get());
        travelerDao.save(traveler);
    }

    @Override
    public RideDto findById(Long id) {
        return convertRideToDto(dao.findById(id).get());
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }


    public Ride convertDtoToRide(Ride ride, RideDto rideDto){

        switch(rideDto.getStatus()) {
            case "AVAILABLE": ride.setStatus(Status.AVAILABLE);
                break;
            case "FULL": ride.setStatus(Status.FULL);
                break;
            case "CANCELLED": ride.setStatus(Status.AVAILABLE);
                break;
            case "COMPLETED": ride.setStatus(Status.COMPLETED);

            default: throw new IllegalArgumentException("illegal status");
        }

        switch (rideDto.getDirection()) {
            case "FIN" -> ride.setDirection(Direction.FIN);
            case "RUS" -> ride.setDirection(Direction.RUS);
            default -> throw new IllegalArgumentException("illegal direction");
        }

        ride.setDate(rideDto.getDate());
        ride.setDriver(travelerDao.findById(rideDto.getDriver().getId()).get());
        ride.setPrice(rideDto.getPrice());
        ride.setDescription(rideDto.getDescription());

        return ride;
    }

    public RideDto convertRideToDto(Ride ride){
        RideDto rideDto = new RideDto();
        rideDto.setId(ride.getId());
        rideDto.setStatus(ride.getStatus().name());
        rideDto.setDate(ride.getDate());
        rideDto.setPrice(ride.getPrice());
        rideDto.setDirection(ride.getDirection().name());

//        rideDto.setDriverId(ride.getDriver().getId());

        rideDto.setDriver(convertTravelerToDto(ride.getDriver()));


        rideDto.setCapacity(ride.getCapacity());
        rideDto.setDescription(ride.getDescription());
        rideDto.setCurrentNumberOfPassengers(ride.getCurrentNumberOfPassengers());

        return rideDto;
    }

    public TravelerDto convertTravelerToDto(Traveler traveler){
        TravelerDto travelerDto = new TravelerDto();

        travelerDto.setId(traveler.getId());
        travelerDto.setFirstname(traveler.getFirstname());
        travelerDto.setSurname(traveler.getSurname());
        travelerDto.setPhoneNumber(traveler.getPhoneNumber());
        travelerDto.setTgUsername(traveler.getTgUsername());
//        travelerDto.setRideDtos(traveler.getRides().stream().map(this::convertRideToDto).toList());

        return travelerDto;
    }

}
