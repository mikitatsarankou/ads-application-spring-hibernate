package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.web;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security.SecurityConfig;
import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.repository.impl.config.ApplicationConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SecurityConfig.class, ApplicationConfig.class, WebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
