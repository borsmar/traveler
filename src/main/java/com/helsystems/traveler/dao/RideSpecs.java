package com.helsystems.traveler.dao;

import com.helsystems.traveler.model.Ride;
import com.helsystems.traveler.model.enums.Direction;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RideSpecs {
    public static Specification<Ride> hasCapacity(Integer capacity) {
        if (capacity == null) {
            return (ride, cq, cb) ->
                    cb.greaterThan(ride.get("capacity"), 0);
        } else {
            return (ride, cq, cb) ->
                    cb.equal(ride.get("capacity"), capacity);
        }
    }


    public static Specification<Ride> hasDirection(String direction) {
        if (direction == null) {
            return (ride, cq, cb) -> cb.isNotNull(ride.get("direction"));
        }
        return (ride, cq, cb) -> cb.equal(ride.get("direction"), Direction.valueOf(direction));
    }

    public static Specification<Ride> hasStatus(String status) {
        if (status == null) {
            return (ride, cq, cb) -> cb.isNotNull(ride.get("status"));
        }
        return (ride, cq, cb) -> cb.equal(ride.get("direction"), Direction.valueOf(status));
    }


    public static Specification<Ride> isBetweenDateAndTime(LocalDate dateBefore, LocalDate dateAfter) {
        if (dateBefore == null && dateAfter == null) {
            return (ride, cq, cb) -> cb.isNotNull(ride.get("date"));
        }
        if (dateBefore == null) {
            dateBefore = LocalDate.now().plusMonths(1L);
        }
        if (dateAfter == null) {
            dateAfter = LocalDate.now().minusDays(1L);
        }

        LocalDate finalDateAfter = dateAfter;
        LocalDate finalDateBefore = dateBefore;

        return (ride, cq, cb) -> cb.between(ride.get("date"), finalDateAfter, finalDateBefore);
    }

}
