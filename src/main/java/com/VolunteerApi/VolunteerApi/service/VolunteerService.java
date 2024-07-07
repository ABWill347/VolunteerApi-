package com.VolunteerApi.VolunteerApi.service;

import com.VolunteerApi.VolunteerApi.domain.Volunteer;
import com.VolunteerApi.VolunteerApi.exception.ServiceException;
import com.VolunteerApi.VolunteerApi.repos.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    public Volunteer addVolunteer(Volunteer volunteer) {
        if (volunteer.getFirstName() == null || volunteer.getFirstName().isEmpty() ||
                volunteer.getLastName() == null || volunteer.getLastName().isEmpty()) {
            throw new ServiceException("Volunteer first name and last name cannot be empty.", HttpStatus.BAD_REQUEST);
        }if (volunteer.getEmail() == null || volunteer.getEmail().isEmpty()) {
            throw new ServiceException("Volunteer email cannot be empty.", HttpStatus.BAD_REQUEST);
        }if (volunteer.getPhone() == null || volunteer.getPhone().isEmpty()) {
            throw new ServiceException("Volunteer phone cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        return volunteerRepository.save(volunteer);
    }

    public List<Volunteer> getAllVolunteers() {
        List<Volunteer> volunteers = (List<Volunteer>) volunteerRepository.findAll();
        if (volunteers.isEmpty()) {
            throw new ServiceException("No volunteers found.", HttpStatus.NOT_FOUND);
        }
        return volunteers;
    }

    public Optional<Volunteer> getVolunteerById(Long id) {
        if (id == null) {
            throw new ServiceException("Volunteer id cannot be null.", HttpStatus.BAD_REQUEST);
        }

        Optional<Volunteer> volunteerOptional = volunteerRepository.findById(id);
        if (volunteerOptional.isEmpty()) {
            throw new ServiceException("Volunteer not found.", HttpStatus.NOT_FOUND);
        }

        return volunteerOptional;
    }

    public void deleteVolunteerById(Long id) {
        if (id == null) {
            throw new ServiceException("Volunteer id cannot be null.", HttpStatus.BAD_REQUEST);
        }

        Optional<Volunteer> volunteerOptional = volunteerRepository.findById(id);
        if (volunteerOptional.isEmpty()) {
            throw new ServiceException("Volunteer not found.", HttpStatus.NOT_FOUND);
        }

        volunteerRepository.deleteById(id);
    }
    public void updateVolunteer(Volunteer volunteer) {
        if (volunteer.getFirstName() == null || volunteer.getFirstName().isEmpty() ||
                volunteer.getLastName() == null || volunteer.getLastName().isEmpty()) {
            throw new ServiceException("Volunteer first name and last name cannot be empty.", HttpStatus.BAD_REQUEST);
        }
        volunteerRepository.save(volunteer);
    }
}


