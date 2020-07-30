package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.GenericDAO;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UsernameNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.User;

import java.util.List;

public interface UserRepository extends GenericDAO<User, Integer> {

    User getByUsername(String username) throws UsernameNotFoundException;

    List<User> getByRole(String role);
}
