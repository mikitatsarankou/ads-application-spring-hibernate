package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RegionNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location.RegionService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.RegionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public List<RegionDto> getAll(){
        return regionService.getAll();
    }

    @GetMapping(value = "/{id}")
    public RegionDto getById(@PathVariable int id) throws RegionNotFoundException {
        return regionService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody RegionDto regionDto) throws RegionNotFoundException {
        regionService.update(regionDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RegionDto regionDto) {
        regionService.create(regionDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) throws RegionNotFoundException {
        regionService.delete(id);
    }
}
