package com.VolunteerApi.VolunteerApi.service;

import com.VolunteerApi.VolunteerApi.domain.Location;
import com.VolunteerApi.VolunteerApi.exception.ServiceException;
import com.VolunteerApi.VolunteerApi.repos.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location addLocation(Location location) {
        if (location.getName() == null || location.getName().isEmpty()) {
            throw new ServiceException("Location name cannot be empty.", HttpStatus.BAD_REQUEST);
        } if (location.getDate() == null || location.getDate().isEmpty()) {
            throw new ServiceException("Location date cannot be empty.", HttpStatus.BAD_REQUEST);
        } if (location.getTime() == null || location.getTime().isEmpty()) {
            throw new ServiceException("Location time cannot be empty.", HttpStatus.BAD_REQUEST);
        } if (location.getPhone() == null || location.getPhone().isEmpty()) {
            throw new ServiceException("Location phone cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        return locationRepository.save(location);
    }

    public List<Location> getAllLocations() {
        List<Location> locations = (List<Location>) locationRepository.findAll();
        if (locations.isEmpty()) {
            throw new ServiceException("No locations found.", HttpStatus.NOT_FOUND);
        }
        return locations;
    }

    public Optional<Location> getLocationById(Long id) {
        if (id == null) {
            throw new ServiceException("Location id cannot be null.", HttpStatus.BAD_REQUEST);
        }
        return locationRepository.findById(id);
    }

    public Location updateLocation(Long id, Location updatedLocation) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            Location existingLocation = optionalLocation.get();
            if (updatedLocation.getName() == null || updatedLocation.getName().isEmpty()) {
                throw new ServiceException("Location name cannot be empty.", HttpStatus.BAD_REQUEST);
            }existingLocation.setName(updatedLocation.getName());
            if (updatedLocation.getDate() == null) {
                throw new ServiceException("Location date cannot be null.", HttpStatus.BAD_REQUEST);
            }existingLocation.setDate(updatedLocation.getDate());
            if (updatedLocation.getTime() == null) {
                throw new ServiceException("Location time cannot be null.", HttpStatus.BAD_REQUEST);
            }existingLocation.setTime(updatedLocation.getTime());
            if (updatedLocation.getPhone() == null || updatedLocation.getPhone().isEmpty()) {
                throw new ServiceException("Location phone cannot be empty.", HttpStatus.BAD_REQUEST);
            }existingLocation.setPhone(updatedLocation.getPhone());
            return locationRepository.save(existingLocation);
        } else {
            throw new ServiceException("Location not found.", HttpStatus.NOT_FOUND);
        }
    }




    public void deleteLocation(Long id) {
        Optional<Location> optionalLocation = locationRepository.findById(id);
        if (optionalLocation.isPresent()) {
            locationRepository.delete(optionalLocation.get());
        } else {
            throw new ServiceException("Location not found.", HttpStatus.NOT_FOUND);
        }
    }
}



