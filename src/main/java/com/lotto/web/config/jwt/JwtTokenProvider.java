package com.lotto.web.config.jwt;

import com.lotto.web.constants.UserRole;
import com.lotto.web.security.ExtendedUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    private final ExtendedUserDetailsService userService;

    public String create(String userAgent, String id, UserRole role) {
        Claims claims = Jwts.claims().setSubject(id);
        claims.put("role", role.name());
        claims.put("agent", userAgent);
        Date issuedAt = new Date();
        Date expireAt = new Date(issuedAt.getTime() + jwtConfig.getValidTime().toMillis());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expireAt)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getEncodedSecret())
                .compact();
    }

    public String resolve(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            return token.substring(7);
        }
        return token;
    }

    public boolean isValid(String token) {
        try {
            log.info("valid... {}", token);
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getEncodedSecret())
                    .parseClaimsJws(token);
            log.info("{}",claims.getBody().getExpiration());
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(jwtConfig.getEncodedSecret())
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userService.getByUserId(getUserId(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    private UserDetails getDetail(String userId) {
        return userService.loadUserByUsername(userId);
    }
}
