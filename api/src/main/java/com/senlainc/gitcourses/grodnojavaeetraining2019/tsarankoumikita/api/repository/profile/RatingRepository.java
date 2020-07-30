package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.    profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.GenericDAO;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RatingNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Rating;

public interface RatingRepository extends GenericDAO<Rating, Integer> {

    Rating getByProfileId(int profileId) throws RatingNotFoundException;
}
