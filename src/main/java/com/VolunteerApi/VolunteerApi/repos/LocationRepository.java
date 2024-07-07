package com.VolunteerApi.VolunteerApi.repos;

import com.VolunteerApi.VolunteerApi.domain.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}

