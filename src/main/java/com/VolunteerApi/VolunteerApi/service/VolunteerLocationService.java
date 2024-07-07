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
        Volunteer volunteer = volunteerService.getVolunteerById(volunteerId)
                .orElseThrow(() -> new ServiceException("Volunteer not found.", HttpStatus.NOT_FOUND));

        Location location = locationService.getLocationById(locationId)
                .orElseThrow(() -> new ServiceException("Location not found.", HttpStatus.NOT_FOUND));

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
            // Validate volunteerId and locationId
            if (volunteerId == null) {
                throw new ServiceException("Volunteer id cannot be null.", HttpStatus.BAD_REQUEST);
            }
            if (locationId == null) {
                throw new ServiceException("Location id cannot be null.", HttpStatus.BAD_REQUEST);
            }

            // Check if volunteer exists
            Volunteer volunteer = volunteerService.getVolunteerById(volunteerId)
                    .orElseThrow(() -> new ServiceException("Volunteer not found for id: " + volunteerId, HttpStatus.NOT_FOUND));

            // Check if location exists
            // Note: This assumes locationService.getLocationById(locationId) returns an Optional<Location>
            locationService.getLocationById(locationId)
                    .orElseThrow(() -> new ServiceException("Location not found for id: " + locationId, HttpStatus.NOT_FOUND));

            // Remove volunteer from location
            volunteerLocationRepository.deleteByVolunteerIdAndLocationId(volunteerId, locationId);
        } catch (ServiceException e) {
            // Rethrow ServiceException to be handled by ControllerAdvice
            throw e;
        } catch (Exception e) {
            // Catch any unexpected exceptions and wrap them in a ServiceException with 500 status
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





