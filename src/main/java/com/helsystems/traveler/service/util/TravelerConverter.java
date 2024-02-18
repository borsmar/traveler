package com.helsystems.traveler.service.util;

import com.helsystems.traveler.dto.TravelerDto;
import com.helsystems.traveler.model.Traveler;
import org.springframework.stereotype.Component;

@Component
public class TravelerConverter {
    public Traveler convertDtoTotraveler(TravelerDto travelerDto){
        Traveler traveler = new Traveler();
        traveler.setId(travelerDto.getId());
        traveler.setFirstname(travelerDto.getFirstname());
        traveler.setSurname(travelerDto.getSurname());
        traveler.setPhoneNumber(travelerDto.getPhoneNumber());
        traveler.setTgUsername(travelerDto.getTgUsername());
        traveler.setPhotoUrl(travelerDto.getPhotoUrl());
//        traveler.setRides(travelerDto.getRideDtos().stream().map(this::convertDtoToRide).toList());

        return traveler;
    }

    public TravelerDto convertTravelerToDto(Traveler traveler){
        TravelerDto travelerDto = new TravelerDto();
        travelerDto.setId(traveler.getId());
        travelerDto.setFirstname(traveler.getFirstname());
        travelerDto.setSurname(traveler.getSurname());
        travelerDto.setPhoneNumber(traveler.getPhoneNumber());
        travelerDto.setPhotoUrl(traveler.getPhotoUrl());

//        travelerDto.setRideDtos(traveler.getRides().stream().map(this::convertRideToDto).toList());

        return travelerDto;
    }

}
