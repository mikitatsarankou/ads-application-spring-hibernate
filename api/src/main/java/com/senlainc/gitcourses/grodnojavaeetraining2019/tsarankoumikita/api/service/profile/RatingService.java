package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.ProfileNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RatingNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.RatingDto;

import java.util.List;

public interface RatingService {

    RatingDto getById(int id) throws RatingNotFoundException;

    List<RatingDto> getAll();

    RatingDto getByProfileId(int profileId) throws RatingNotFoundException, ProfileNotFoundException;

    void create(RatingDto ratingDto);

    void update(RatingDto ratingDto) throws RatingNotFoundException;

    void delete(int id) throws RatingNotFoundException;

    void addPoints(int id, int points) throws ProfileNotFoundException, RatingNotFoundException;
}
