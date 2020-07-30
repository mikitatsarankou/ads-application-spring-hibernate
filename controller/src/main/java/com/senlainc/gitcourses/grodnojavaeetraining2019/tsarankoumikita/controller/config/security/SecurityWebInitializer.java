package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@ComponentScan({"com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security",
                "com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.serviceimpl.systemuser"})
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
}
