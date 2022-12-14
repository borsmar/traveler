package com.helsystems.traveler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Data
@EqualsAndHashCode(exclude = {"rides"})
public class Traveler implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;
    @Column
    private String firstname;
    @Column
    private String surname;
    @Column
    private String phoneNumber;
    @Column
    private String tgUsername;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Ride> rides;

}

