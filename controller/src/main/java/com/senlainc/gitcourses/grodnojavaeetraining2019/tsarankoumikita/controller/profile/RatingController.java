package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ProfileNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RatingNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile.RatingService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.RatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ratings")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @GetMapping
    public List<RatingDto> getAll(){
        return ratingService.getAll();
    }

    @GetMapping(value = "/{id}")
    public RatingDto getById(@PathVariable int id) throws RatingNotFoundException {
        return ratingService.getById(id);
    }

    @GetMapping(value = "/from-profile/{id}")
    public RatingDto getByProfileId(@PathVariable int id) throws ProfileNotFoundException, RatingNotFoundException {
        return ratingService.getByProfileId(id);
    }

    @PutMapping
    public void update(@RequestBody RatingDto ratingDto) throws RatingNotFoundException {
        ratingService.update(ratingDto);
    }

    @PutMapping(value = "/add-points/{id}/{points}")
    public void addPointsForSale(
            @PathVariable int id,
            @PathVariable int points) throws ProfileNotFoundException, RatingNotFoundException {
        ratingService.addPoints(id, points);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RatingDto ratingDto) {
        ratingService.create(ratingDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) throws RatingNotFoundException {
        ratingService.delete(id);
    }
}
