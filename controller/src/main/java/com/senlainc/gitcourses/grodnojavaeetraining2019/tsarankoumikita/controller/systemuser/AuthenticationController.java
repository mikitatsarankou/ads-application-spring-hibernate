package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.systemuser;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security.JwtTokenInstruction;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.dto.systemuser.AuthenticationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenInstruction jwtTokenInstruction;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity authentication(@RequestBody AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        String token = jwtTokenInstruction.generateToken(userDetails);

        Map<Object, Object> response = new HashMap<>();

        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
