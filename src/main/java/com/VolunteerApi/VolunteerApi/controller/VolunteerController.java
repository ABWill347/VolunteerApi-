package com.VolunteerApi.VolunteerApi.controller;

import com.VolunteerApi.VolunteerApi.domain.Volunteer;
import com.VolunteerApi.VolunteerApi.exception.ServiceException;
import com.VolunteerApi.VolunteerApi.service.LocationService;
import com.VolunteerApi.VolunteerApi.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private LocationService locationService;

    @PostMapping("/volunteers")
    public ResponseEntity<Object> addVolunteer(@RequestBody Volunteer volunteer) {
        try {
            Volunteer savedVolunteer = volunteerService.addVolunteer(volunteer);
            return ResponseEntity.ok(savedVolunteer);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/volunteers")
    public ResponseEntity<Object> getAllVolunteers() {
        try {
            List<Volunteer> volunteers = volunteerService.getAllVolunteers();
            return ResponseEntity.ok(volunteers);
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @GetMapping("/volunteers/{id}")
    public ResponseEntity<Object> getVolunteerById(@PathVariable Long id) {
        try {
            Optional<Volunteer> volunteerOptional = volunteerService.getVolunteerById(id);
            if (volunteerOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(volunteerOptional.get());
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }


    @PutMapping("/volunteers/{volunteerId}")
    public ResponseEntity<Object> updateVolunteer(@PathVariable Long volunteerId, @RequestBody Volunteer volunteer) {
        try {
            Optional<Volunteer> volunteerOptional = volunteerService.getVolunteerById(volunteerId);
            if (volunteerOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            volunteer.setId(volunteerId);
            volunteerService.updateVolunteer(volunteer);
            return ResponseEntity.ok("Volunteer updated successfully.");
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }

    @DeleteMapping("/volunteers/{id}")
    public ResponseEntity<Object> deleteVolunteerById(@PathVariable Long id) {
        try {
            volunteerService.deleteVolunteerById(id);
            return ResponseEntity.ok("Volunteer deleted successfully.");
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }
    }
}




