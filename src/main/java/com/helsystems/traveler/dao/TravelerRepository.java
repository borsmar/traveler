package com.helsystems.traveler.dao;

import com.helsystems.traveler.model.Ride;
import com.helsystems.traveler.model.Traveler;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelerRepository extends CrudRepository<Traveler, Long> {

    List<Traveler> findTravelersByRides(Ride ride);
}

