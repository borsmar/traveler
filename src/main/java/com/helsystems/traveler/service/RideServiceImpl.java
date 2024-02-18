package com.helsystems.traveler.service;

import com.helsystems.traveler.dao.RideRepository;
import com.helsystems.traveler.dao.TravelerRepository;
import com.helsystems.traveler.dto.RideDto;
import com.helsystems.traveler.model.Ride;
import com.helsystems.traveler.model.Traveler;
import com.helsystems.traveler.service.util.RideConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.helsystems.traveler.dao.RideSpecs.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class RideServiceImpl implements RideService {
    @Autowired
    private RideRepository dao;

    @Autowired
    private TravelerRepository travelerDao;

    @Autowired
    private RideConverter rideConverter;


    @Override
    public List<RideDto> findAll() {
        List<Ride> rideList = (List<Ride>) dao.findAll();
        return rideList.stream().map(rideConverter::convertRideToDto).collect(Collectors.toList());
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
                                .and(isBetweenDateAndTime(dateBefore, dateAfter)
                                        .and(hasStatus(status)))
                );
        return rideList.stream().map(rideConverter::convertRideToDto).collect(Collectors.toList());
    }


    @Override
    public RideDto save(RideDto rideDto) {
        //add checking auth to this method or to controller
        dao.save(rideConverter.convertDtoToRide(new Ride(), rideDto));
        return rideDto;
    }

    @Override
    public RideDto update(RideDto rideDto, Long rideId) {
        Ride ride = rideConverter.convertDtoToRide(dao.findById(rideId).orElseThrow(()-> new NoSuchElementException("Ride with ID " + rideId + " not found")), rideDto);
        return rideConverter.convertRideToDto(dao.save(ride));
    }

    @Override
    public void addPassengerToRide(Long passengerId, Long rideId) {
        Traveler traveler = travelerDao.findById(passengerId).orElseThrow(()-> new NoSuchElementException("Traveler with ID " + passengerId + " not found"));
        traveler.getRides().add(dao.findById(rideId).orElseThrow(()-> new NoSuchElementException("Ride with ID " + rideId + " not found")));
        travelerDao.save(traveler);
    }

    @Override
    public RideDto findById(Long id) {
        return rideConverter.convertRideToDto(dao.findById(id).orElseThrow(()-> new NoSuchElementException("Ride with ID " + id + " not found")));
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }

}
