package com.helsystems.traveler.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RideDto {
    private Long id;

    private String status;

    private LocalDateTime date;

    private int price;

    private String direction;

    private Long driverId;

    int capacity;

    private String description;

    private int currentNumberOfPassengers;
}
