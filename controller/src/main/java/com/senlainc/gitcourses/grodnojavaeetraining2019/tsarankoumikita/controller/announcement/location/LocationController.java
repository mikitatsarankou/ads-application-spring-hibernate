package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.LocationNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ObjectNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location.LocationService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<LocationDto> getAll(){
        return locationService.getAll();
    }

    @GetMapping(value = "/{id}")
    public LocationDto getById(@PathVariable int id) throws LocationNotFoundException {
        return locationService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody LocationDto locationDto) throws LocationNotFoundException {
        locationService.update(locationDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody LocationDto locationDto) {
        locationService.create(locationDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) throws LocationNotFoundException {
        locationService.delete(id);
    }

}
