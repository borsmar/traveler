package com.helsystems.traveler.dto;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;

@Data
public class RideDto {
    private Long id;

    private String status;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    private int price;

    private String direction;

    private Long driverId;

    int capacity;

    private String description;

    private int currentNumberOfPassengers;
}
