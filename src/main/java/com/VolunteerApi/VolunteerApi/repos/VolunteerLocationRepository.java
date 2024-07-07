package com.VolunteerApi.VolunteerApi.repos;

import com.VolunteerApi.VolunteerApi.domain.Location;
import com.VolunteerApi.VolunteerApi.domain.VolunteerLocation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VolunteerLocationRepository extends CrudRepository<VolunteerLocation, Long> {

    @Query("SELECT vl.location FROM VolunteerLocation vl WHERE vl.volunteer.id = :volunteerId")
    List<Location> findLocationsByVolunteerId(@Param("volunteerId") Long volunteerId);

    @Modifying
    @Transactional
    @Query("DELETE FROM VolunteerLocation vl WHERE vl.volunteer.id = :volunteerId AND vl.location.id = :locationId")
    void deleteByVolunteerIdAndLocationId(@Param("volunteerId") Long volunteerId, @Param("locationId") Long locationId);

    @Query("SELECT vl FROM VolunteerLocation vl WHERE vl.volunteer.id = :volunteerId")
    List<VolunteerLocation> findByVolunteerId(Long volunteerId);

    @Query("SELECT vl FROM VolunteerLocation vl WHERE vl.location.id = :locationId")
    List<VolunteerLocation> findByLocationId(@Param("locationId") Long locationId);

}




