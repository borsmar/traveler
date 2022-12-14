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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TravelerServiceImpl implements TravelerService {

    @Autowired
    private TravelerRepository dao;


    @Autowired
    private RideRepository rideDao;


    @Override
    public List<TravelerDto> findAll() {
        List<Traveler> travelersList = (List<Traveler>) dao.findAll();
        return travelersList.stream().map(this::convertTravelerToDto).collect(Collectors.toList());
    }


    @Override
    public List<TravelerDto> findTravelersByRide(Long rideId) {
        List<Traveler> travelersList =  dao.findTravelersByRides(rideDao.findById(rideId).get());
        return travelersList.stream().map(this::convertTravelerToDto).collect(Collectors.toList());
    }

    @Override
    public TravelerDto save(TravelerDto travelerDto) {
        dao.save(convertDtoTotraveler(travelerDto));
        return travelerDto;
    }

    @Override
    public TravelerDto update(TravelerDto travelerDto, Long id) {
        Traveler traveler = convertDtoTotraveler(travelerDto);
        traveler.setId(id);
        return travelerDto;
    }

    @Override
    public TravelerDto findById(Long id) {
        return convertTravelerToDto(dao.findById(id).get());
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }


    public Traveler convertDtoTotraveler(TravelerDto travelerDto){
        Traveler traveler = new Traveler();
        traveler.setFirstname(travelerDto.getFirstname());
        traveler.setSurname(travelerDto.getSurname());
        traveler.setPhoneNumber(travelerDto.getPhoneNumber());
//        traveler.setRides(travelerDto.getRideDtos().stream().map(this::convertDtoToRide).toList());

        return traveler;
    }

    public TravelerDto convertTravelerToDto(Traveler traveler){
        TravelerDto travelerDto = new TravelerDto();

        travelerDto.setId(traveler.getId());
        travelerDto.setFirstname(traveler.getFirstname());
        travelerDto.setSurname(traveler.getSurname());
        travelerDto.setPhoneNumber(traveler.getPhoneNumber());
        System.out.println(traveler.getRides().size());
//        travelerDto.setRideDtos(traveler.getRides().stream().map(this::convertRideToDto).toList());

        return travelerDto;
    }

    public Ride convertDtoToRide(RideDto rideDto){
        Ride ride = new Ride();

        switch (rideDto.getStatus()) {
            case "AVAILABLE" -> ride.setStatus(Status.AVAILABLE);
            case "UNAVAILABLE" -> ride.setStatus(Status.FULL);
            case "CANCELLED" -> ride.setStatus(Status.CANCELLED);
            case "COMPLETED" -> ride.setStatus(Status.COMPLETED);
            default -> throw new IllegalArgumentException("illegal status");
        }

        switch (rideDto.getDirection()) {
            case "FIN" -> ride.setDirection(Direction.FIN);
            case "RUS" -> ride.setDirection(Direction.RUS);
            default -> throw new IllegalArgumentException("illegal direction");
        }


        ride.setDate(rideDto.getDate());
        ride.setDriver(dao.findById(rideDto.getDriverId()).get());
        ride.setPrice(rideDto.getPrice());
        ride.setCapacity(rideDto.getCapacity());
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
        rideDto.setDriverId(ride.getDriver().getId());
        rideDto.setDescription(ride.getDescription());

        return rideDto;
    }

}
