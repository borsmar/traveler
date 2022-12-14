package com.helsystems.traveler.dto;

import lombok.Data;

import java.util.List;

@Data
public class TravelerDto {
    private long id;
    private String firstname;
    private String surname;
    private String tgUsername;
    private String phoneNumber;

}
