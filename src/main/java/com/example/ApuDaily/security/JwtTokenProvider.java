package com.example.ApuDaily.security;

import com.example.ApuDaily.exception.ApiException;
import com.example.ApuDaily.exception.ErrorMessage;
import com.example.ApuDaily.user.model.User;
import com.example.ApuDaily.user.service.UserUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.Objects.isNull;

@Component
public class JwtTokenProvider {
    @Autowired
    SecurityProps securityProps;

    @Autowired
    UserUtil userUtil;

    public String generateToken(User user){
        String username = user.getUsername();
        Date currentDate = new Date();
        Date expirationDate =
                new Date(currentDate.getTime() + securityProps.Jwt().expirationMilliseconds());

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(securityProps.Jwt().secret().getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromJwt(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(securityProps.Jwt().secret().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(securityProps.Jwt().secret().getBytes()))
                    .build()
                    .parseClaimsJws(token);

            String signature = claims.getSignature();
            if(isNull(signature)){
                throw new ApiException(ErrorMessage.INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }

            Date issuedAt = claims.getBody().getIssuedAt();
            Date expiredAt = claims.getBody().getExpiration();
            if ((expiredAt.getTime() - issuedAt.getTime())
                    != securityProps.Jwt().expirationMilliseconds()) {
                throw new ApiException(ErrorMessage.INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }

            String usernameOrEmail = claims.getBody().getSubject();
            if (userUtil.getUserByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                    .isEmpty()) {
                throw new ApiException(ErrorMessage.INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
            }

        } catch (ExpiredJwtException
                 | UnsupportedJwtException
                 | MalformedJwtException
                 | SignatureException
                 | IllegalArgumentException ex) {
            throw new ApiException(ErrorMessage.INVALID_TOKEN, null, HttpStatus.UNAUTHORIZED);
        }

        return true;
    }
}