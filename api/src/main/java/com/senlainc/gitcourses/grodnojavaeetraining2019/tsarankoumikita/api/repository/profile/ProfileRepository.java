package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.profile;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UserNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.GenericDAO;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.profile.ProfileDto;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.profile.Profile;

public interface ProfileRepository extends GenericDAO<Profile, Integer> {

    Profile getProfileByUserId(int userId) throws UserNotFoundException;
}
