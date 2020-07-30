package com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.controller.config.security;

import com.senlainc.gitcourses.grodnojavaeetraining2019.tsarankoumikita.entity.systemuser.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@PropertySource({"classpath:application.properties"})
@Component
public class JwtTokenInstruction {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${token.expire.time}")
    private long tokenExpireTimeInMilliseconds;

    @Value("${secret.key}")
    private String secretKey;

    private final SignatureAlgorithm signatureAlgorithm  = SignatureAlgorithm.HS256;

    @PostConstruct
    protected Key getSecretKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
    }

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + tokenExpireTimeInMilliseconds);

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("roles", getNamesOfTheRoles((List<Role>) userDetails.getAuthorities()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(signatureAlgorithm, getSecretKey())
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean verifyToken(String token) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token);
        return claimsJws.getBody().getExpiration().after(new Date());
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private List<String> getNamesOfTheRoles(List<Role> userRoles) {
        List<String> roles = new ArrayList<>();

        userRoles.forEach(x -> roles.add(x.getName()));

        return roles;
    }
}
