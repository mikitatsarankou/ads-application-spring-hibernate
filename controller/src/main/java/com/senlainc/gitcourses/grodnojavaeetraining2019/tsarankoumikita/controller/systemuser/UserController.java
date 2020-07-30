package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.*;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UsernameNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RoleNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.UserNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.UserService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getAll(){
        return userService.getAll();
    }

    @GetMapping(value = "/with-username")
    public UserDto getByUsername(@RequestParam String username) throws UsernameNotFoundException {
        return userService.getByUsername(username);
    }

    @GetMapping(value = "/with-role")
    public List<UserDto> getByRole(@RequestParam String role) throws UsernameNotFoundException, RoleNotFoundException {
        return userService.getByRole(role);
    }

    @GetMapping(value = "/{id}")
    public UserDto getById(@PathVariable int id) throws UserNotFoundException {
        return userService.getById(id);
    }

    @PutMapping
    public void update(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException {
        userService.update(userDto, userDetails);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) throws RoleNotFoundException, UsernameAlreadyExistsException {
        if (userService.isUserHaveAdminRole(userDto)) {
            if (userDetails != null && userService.isUserPrincipalHaveAdminRole(userDetails)) {
                userService.create(userDto);
            }
            else throw new AccessDeniedException("unable to create user with role admin if you are not admin");
        } else userService.create(userDto);

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) throws UserNotFoundException, AccessDeniedException {
        userService.delete(id, userDetails);
    }
}
