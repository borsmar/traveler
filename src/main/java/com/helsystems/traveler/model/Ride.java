package com.helsystems.traveler.model;

import com.helsystems.traveler.model.enums.Direction;
import com.helsystems.traveler.model.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data

public class Ride implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private Status status;

    @Column
    private String description;

    @Column
    private LocalDate date;

    @Column
    private int price;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Direction direction;

    @OneToOne
    private Traveler driver;
    @Column
    int capacity;

    @Column
    int currentNumberOfPassengers;

}
