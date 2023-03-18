package com.saludapp.seguridad.jwt;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.saludapp.entidades.Usuario;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.saludapp.constantes.SecurityConstant;

@Component
public class JwtTokenProvider{
    
    @Value("${jwt.secret}")
    private String secret;


    public String generateToken(Usuario usuario){
        String[] claims = getClaimsFromUser(usuario);

        return JWT.create()
                    .withIssuer(SecurityConstant.SALUDAPP)
                    .withAudience(SecurityConstant.SALUDAPP_ADMINISTRATION)
                .withIssuedAt(new Date())
                    .withSubject(usuario.getUsername())
                .withArrayClaim(SecurityConstant.AUTHORITIES, claims)
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + Long.parseLong(SecurityConstant.EXPIRATION_TIME)))
                    .sign(Algorithm.HMAC512(secret.getBytes()));

    } 

    public Authentication getAuthentication(String username, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken userPasswordAuthToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
        userPasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPasswordAuthToken;
    }

    public boolean isTokenValid(String username, String token){
        JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(username) && !isTokenExpired(verifier, token);
    }

    public String getSubject(String token){
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token){
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    public List<GrantedAuthority> getAuthorities(String token){
        List<String> claims = getClaimsFromToken(token);
        List<GrantedAuthority> authorities = new ArrayList<>();
        claims.forEach(claim -> {
            authorities.add(new SimpleGrantedAuthority(claim));
        });
        return authorities;
    }

    private List<String> getClaimsFromToken(String token){
        JWTVerifier verifier = getJWTVerifier();
        Map<String, Claim> claims =  verifier.verify(token).getClaims();
            if(claims.containsKey(SecurityConstant.AUTHORITIES)){
                System.out.println("Se encontraron claims");
                Claim authClaim = claims.get(SecurityConstant.AUTHORITIES);
               // return claims.get(SecurityConstant.AUTHORITIES).asList(String.class);
                return authClaim.asList(String.class);
            }else{
                System.out.println("No se encontraron claims");
                return new ArrayList<>();
            }
    }

    private JWTVerifier getJWTVerifier(){
        JWTVerifier verifier;
        try{
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(SecurityConstant.SALUDAPP).build();
        }catch(JWTVerificationException e){
            throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
        }
        return verifier;
    }

    private String[] getClaimsFromUser(Usuario user){
       ArrayList<String> authorities = new ArrayList<>();
       for(String auth : user.getAuthorities()){
           authorities.add(auth);
       }
        return authorities.toArray(new String[0]);
    }
}