package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security.filter.TokenFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenInstruction jwtTokenInstruction;

    public JwtConfigurer(JwtTokenInstruction jwtTokenInstruction) {
        this.jwtTokenInstruction = jwtTokenInstruction;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        TokenFilter tokenFilter = new TokenFilter(jwtTokenInstruction);
        httpSecurity.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
