package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security.filter;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security.JwtTokenInstruction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TokenFilter extends GenericFilterBean {

    private JwtTokenInstruction jwtTokenInstruction;

    public TokenFilter(JwtTokenInstruction jwtTokenInstruction) {
        this.jwtTokenInstruction = jwtTokenInstruction;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        String token = jwtTokenInstruction.getBearerToken((HttpServletRequest) req);
        if (token != null && jwtTokenInstruction.verifyToken(token)) {
            Authentication authentication = jwtTokenInstruction.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(req, res);
    }
}
