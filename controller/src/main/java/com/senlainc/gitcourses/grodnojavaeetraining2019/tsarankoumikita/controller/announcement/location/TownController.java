package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.announcement.location;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.TownNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.announcement.location.TownService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.announcement.location.TownDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/towns")
public class TownController {

    @Autowired
    private TownService townService;

    @GetMapping
    public List<TownDto> getAll(){
        return townService.getAll();
    }

    @GetMapping(value = "/{id}")
    public TownDto getById(@PathVariable int id) throws TownNotFoundException {
        return townService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody TownDto townDto) throws TownNotFoundException {
        townService.update(townDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody TownDto townDto) {
        townService.create(townDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) throws TownNotFoundException {
        townService.delete(id);
    }
}
