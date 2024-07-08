package com.VolunteerApi.VolunteerApi.service;

import com.VolunteerApi.VolunteerApi.domain.Location;
import com.VolunteerApi.VolunteerApi.domain.Volunteer;
import com.VolunteerApi.VolunteerApi.domain.VolunteerLocation;
import com.VolunteerApi.VolunteerApi.exception.ServiceException;
import com.VolunteerApi.VolunteerApi.repos.VolunteerLocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class VolunteerLocationService {

    private final VolunteerLocationRepository volunteerLocationRepository;
    private final VolunteerService volunteerService;
    private final LocationService locationService;

    @Autowired
    public VolunteerLocationService(VolunteerLocationRepository volunteerLocationRepository,
                                    VolunteerService volunteerService,
                                    LocationService locationService) {
        this.volunteerLocationRepository = volunteerLocationRepository;
        this.volunteerService = volunteerService;
        this.locationService = locationService;
    }

    @Transactional
    public void assignVolunteerToLocation(Long volunteerId, Long locationId) {
        Optional<Volunteer> optionalVolunteer = volunteerService.getVolunteerById(volunteerId);
        if (optionalVolunteer.isEmpty()) {
            throw new ServiceException("Volunteer not found.", HttpStatus.NOT_FOUND);
        }Optional<Location> optionalLocation = locationService.getLocationById(locationId);
        if (optionalLocation.isEmpty()) {
            throw new ServiceException("Location not found.", HttpStatus.NOT_FOUND);
        }Volunteer volunteer = optionalVolunteer.get();
        Location location = optionalLocation.get();
        VolunteerLocation volunteerLocation = new VolunteerLocation(volunteer, location);
        volunteerLocationRepository.save(volunteerLocation);
    }




    public List<VolunteerLocation> getVolunteerLocations(Long volunteerId) {
        if (volunteerId == null) {
            throw new ServiceException("Volunteer id cannot be null.", HttpStatus.BAD_REQUEST);
        }if (volunteerService.getVolunteerById(volunteerId).isEmpty()) {
            throw new ServiceException("Volunteer not found.", HttpStatus.NOT_FOUND);
        }
        return volunteerLocationRepository.findByVolunteerId(volunteerId);
    }

    public void removeVolunteerFromLocation(Long volunteerId, Long locationId) {
        try {
            if (volunteerId == null) {
                throw new ServiceException("Volunteer id cannot be null.", HttpStatus.BAD_REQUEST);
            }if (locationId == null) {
                throw new ServiceException("Location id cannot be null.", HttpStatus.BAD_REQUEST);
            }Volunteer volunteer = volunteerService.getVolunteerById(volunteerId)
                    .orElseThrow(() -> new ServiceException("Volunteer not found for id: " + volunteerId, HttpStatus.NOT_FOUND));

            locationService.getLocationById(locationId)
                    .orElseThrow(() -> new ServiceException("Location not found for id: " + locationId, HttpStatus.NOT_FOUND));
            volunteerLocationRepository.deleteByVolunteerIdAndLocationId(volunteerId, locationId);
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<VolunteerLocation> getVolunteersByLocation(Long locationId) {
        if (locationId == null) {
            throw new ServiceException("Location id cannot be null.", HttpStatus.BAD_REQUEST);
        }
        return volunteerLocationRepository.findByLocationId(locationId);
    }
}





