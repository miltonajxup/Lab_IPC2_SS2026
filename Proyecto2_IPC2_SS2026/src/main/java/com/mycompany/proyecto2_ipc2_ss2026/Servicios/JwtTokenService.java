/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioDB;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;

/**
 *
 * @author milton
 */
public class JwtTokenService {
    
    private static final String SECRET = "una-clave-muy-larga-de-al-menos-32-caracteres-para-hs256";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    
    public String generar(UsuarioDB usuario) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(usuario.getDpi())
                .claim("roles", usuario.getRoles().stream().map(Enum::name).collect(Collectors.toSet()))
                .claim("nombre", usuario.getNombre())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(2, ChronoUnit.HOURS)))
                .signWith(key)
                .compact();
    }
    
    public Jws<Claims> validate(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
    
}
