package com.VolunteerApi.VolunteerApi.controller;

import com.VolunteerApi.VolunteerApi.domain.Location;
import com.VolunteerApi.VolunteerApi.exception.ServiceException;
import com.VolunteerApi.VolunteerApi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/locations")
    public ResponseEntity<Object> addLocation(@RequestBody Location location) {
        try {
            Location savedLocation = locationService.addLocation(location);
            return ResponseEntity.ok(savedLocation);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/locations")
    public ResponseEntity<Object> getAllLocations() {
        try {
            List<Location> locations = locationService.getAllLocations();
            return ResponseEntity.ok(locations);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/locations/{id}")
    public ResponseEntity<Object> getLocationById(@PathVariable Long id) {
        try {
            Location location = locationService.getLocationById(id)
                    .orElseThrow(() -> new ServiceException("Location not found", HttpStatus.NOT_FOUND));
            return ResponseEntity.ok(location);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @PutMapping("/locations/{id}")
    public ResponseEntity<Object> updateLocation(@PathVariable Long id, @RequestBody Location location) {
        try {
            Location updatedLocation = locationService.updateLocation(id, location);
            return ResponseEntity.ok(updatedLocation);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/locations/{id}")
    public ResponseEntity<Object> deleteLocation(@PathVariable Long id) {
        try {
            locationService.deleteLocation(id);
            return ResponseEntity.ok("Location deleted successfully.");
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}



