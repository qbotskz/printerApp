//package com.akimatBot.web.helpers;
//
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JWTTokenGenerator {
//    @Value("${spring.security.application.secret}")
//    private String secret;
//
//    public String generateAccessToken(String email, String requestURI) {
//        return JWT
//                .create()
//                .withSubject(email)
//                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 30 * 1000))
//                .withIssuer(requestURI)
//                .sign(Algorithm.HMAC256(secret.getBytes()));
//    }
//
//    public String generateRefreshToken(String email, String requestURI) {
//        return JWT
//                .create()
//                .withSubject(email)
//                .withExpiresAt(new Date(System.currentTimeMillis() + 2592000000L))
//                .withIssuer(requestURI)
//                .sign(Algorithm.HMAC256(secret.getBytes()));
//    }
//}
