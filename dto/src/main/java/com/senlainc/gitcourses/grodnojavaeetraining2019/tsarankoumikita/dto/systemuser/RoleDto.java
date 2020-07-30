package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.enums.RoleNameDto;

public class RoleDto {

    private Integer id;

    private RoleNameDto name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoleNameDto getName() {
        return name;
    }

    public void setName(RoleNameDto name) {
        this.name = name;
    }

}