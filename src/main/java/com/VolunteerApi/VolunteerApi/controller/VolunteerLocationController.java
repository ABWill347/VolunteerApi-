package com.VolunteerApi.VolunteerApi.controller;

import com.VolunteerApi.VolunteerApi.domain.Volunteer;
import com.VolunteerApi.VolunteerApi.domain.VolunteerLocation;
import com.VolunteerApi.VolunteerApi.exception.ServiceException;
import com.VolunteerApi.VolunteerApi.service.VolunteerLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer")
public class VolunteerLocationController {

    @Autowired
    private VolunteerLocationService volunteerLocationService;

    @PostMapping("/{volunteerId}/locations/{locationId}")
    public ResponseEntity<Object> assignVolunteerToLocation(@PathVariable Long volunteerId, @PathVariable Long locationId) {
        try {
            volunteerLocationService.assignVolunteerToLocation(volunteerId, locationId);
            return ResponseEntity.ok("Volunteer assigned to location successfully.");
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/{volunteerId}/locations")
    public ResponseEntity<Object> getVolunteerLocations(@PathVariable Long volunteerId) {
        try {
            List<VolunteerLocation> locations = volunteerLocationService.getVolunteerLocations(volunteerId);
            return ResponseEntity.ok(locations);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
    @GetMapping("/locations/{locationId}")
    public ResponseEntity<Object> getVolunteersByLocation(@PathVariable Long locationId) {
        try {
            List<VolunteerLocation> volunteers = volunteerLocationService.getVolunteersByLocation(locationId);
            return ResponseEntity.ok(volunteers);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/{volunteerId}/locations/{locationId}")
    public ResponseEntity<Object> removeVolunteerFromLocation(@PathVariable Long volunteerId, @PathVariable Long locationId) {
        try {
            volunteerLocationService.removeVolunteerFromLocation(volunteerId, locationId);
            return ResponseEntity.ok("Volunteer removed from location successfully.");
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

}

