package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RoleNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.User;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto getById(int id) throws RoleNotFoundException;

    List<RoleDto> getAll();

    void create(RoleDto roleDto);

    void update(RoleDto roleDto) throws RoleNotFoundException;

    void delete(int id) throws RoleNotFoundException;

    RoleDto getByName(String name) throws RoleNotFoundException;

    List<User> getUsersByRole(String name) throws RoleNotFoundException;
}
