package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.repository.GenericDAO;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.enums.RoleName;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.Role;

public interface RoleRepository extends GenericDAO<Role, Integer> {

    Role getByName(RoleName name);
}
