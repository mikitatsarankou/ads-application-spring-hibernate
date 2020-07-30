package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.UsernameAlreadyExistsException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.exceptions.notfound.RoleNotFoundException;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.api.service.systemuser.UserService;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(@RequestBody UserDto userDto) throws UsernameAlreadyExistsException, RoleNotFoundException {
        return userService.create(userDto);
    }
}
