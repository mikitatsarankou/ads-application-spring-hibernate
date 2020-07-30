package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RoleNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.RoleService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<RoleDto> getAll(){
        return roleService.getAll();
    }

    @GetMapping(value = "with-name")
    public RoleDto getByName(@RequestParam String name) throws RoleNotFoundException {
        return roleService.getByName(name);
    }
    @GetMapping(value = "/{id}")
    public RoleDto getById(@PathVariable int id) throws RoleNotFoundException {
        return roleService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody RoleDto roleDto) throws RoleNotFoundException {
        roleService.update(roleDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody RoleDto roleDto) {
        roleService.create(roleDto);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id) throws RoleNotFoundException {
        roleService.delete(id);
    }
}
