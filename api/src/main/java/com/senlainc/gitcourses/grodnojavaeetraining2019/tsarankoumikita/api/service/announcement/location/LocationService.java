package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.LocationNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.LocationDto;

import java.util.List;

public interface LocationService {

    LocationDto getById(int id) throws LocationNotFoundException;

    List<LocationDto> getAll();

    void create(LocationDto locationDto);

    void update(LocationDto locationDto) throws LocationNotFoundException;

    void delete(int id) throws LocationNotFoundException;

}
