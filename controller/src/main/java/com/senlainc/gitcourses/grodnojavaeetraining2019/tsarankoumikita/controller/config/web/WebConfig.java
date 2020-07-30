package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan("com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita")
public class WebConfig implements WebMvcConfigurer {

}
