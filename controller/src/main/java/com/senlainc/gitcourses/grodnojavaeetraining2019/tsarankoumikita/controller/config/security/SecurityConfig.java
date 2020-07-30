package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.enums.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@ComponentScan({"com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller",
                "com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.service.impl"})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenInstruction jwtTokenInstruction;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager defaultAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/announcements/**", "/categories/**", "/locations/**",
                                                       "/towns/**", "/regions/**", "/comments/**").permitAll()

                .antMatchers(HttpMethod.POST, "/registration", "/authentication").permitAll()

                .antMatchers(HttpMethod.POST, "/announcements", "/comments", "/profiles")
                .hasAuthority(RoleName.USER.name())

                .antMatchers(HttpMethod.GET, "/profiles", "/users", "/ratings")
                .hasAuthority(RoleName.USER.name())

                .antMatchers(HttpMethod.PUT, "/announcements", "/comments", "/profiles", "/users")
                .hasAuthority(RoleName.USER.name())

                .antMatchers(HttpMethod.DELETE, "/announcements/**", "/comments/**", "/profiles/**", "/users/**")
                .hasAuthority(RoleName.USER.name())

                .anyRequest().hasAuthority(RoleName.ADMIN.name())

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .apply(new JwtConfigurer(jwtTokenInstruction))
                .and()
                .formLogin().disable();
    }
}
