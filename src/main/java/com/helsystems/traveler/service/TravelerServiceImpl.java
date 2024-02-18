package com.helsystems.traveler.service;

import com.helsystems.traveler.dao.RideRepository;
import com.helsystems.traveler.dao.TravelerRepository;
import com.helsystems.traveler.dto.RideDto;
import com.helsystems.traveler.dto.TravelerDto;
import com.helsystems.traveler.model.Ride;
import com.helsystems.traveler.model.Traveler;
import com.helsystems.traveler.model.enums.Direction;
import com.helsystems.traveler.model.enums.Status;
import com.helsystems.traveler.service.util.TravelerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TravelerServiceImpl implements TravelerService {

    @Autowired
    private TravelerRepository dao;

    @Autowired
    private RideRepository rideDao;

    @Autowired
    private TravelerConverter travelerConverter;


    @Override
    public List<TravelerDto> findAll() {
        List<Traveler> travelersList = (List<Traveler>) dao.findAll();
        return travelersList.stream().map(travelerConverter::convertTravelerToDto).collect(Collectors.toList());
    }


    @Override
    public List<TravelerDto> findTravelersByRide(Long rideId) {
        List<Traveler> travelersList =  dao.findTravelersByRides(rideDao.findById(rideId).orElseThrow(()-> new NoSuchElementException("Ride with ID " + rideId + " not found")));
        return travelersList.stream().map(travelerConverter::convertTravelerToDto).collect(Collectors.toList());
    }

    @Override
    public TravelerDto save(TravelerDto travelerDto) {
        dao.save(travelerConverter.convertDtoTotraveler(travelerDto));
        return travelerDto;
    }

    @Override
    public TravelerDto update(TravelerDto travelerDto, Long id) {
        Traveler traveler = travelerConverter.convertDtoTotraveler(travelerDto);
        traveler.setId(id);
        return travelerDto;
    }

    @Override
    public TravelerDto findById(Long id) {
        return travelerConverter.convertTravelerToDto(dao.findById(id).orElseThrow(() -> new NoSuchElementException("Traveler not found with id " + id)));
    }

    @Override
    public void deleteById(Long id) {
        dao.deleteById(id);
    }
}
