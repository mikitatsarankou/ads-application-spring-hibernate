package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser;

import java.util.List;

public class UserDto {

    private Integer id;

    private String username;

    private String password;

    private List<RoleDto> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

}
