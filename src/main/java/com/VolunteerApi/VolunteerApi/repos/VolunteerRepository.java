package com.VolunteerApi.VolunteerApi.repos;

import com.VolunteerApi.VolunteerApi.domain.Volunteer;
import org.springframework.data.repository.CrudRepository;

public interface VolunteerRepository extends CrudRepository<Volunteer, Long> {
}

