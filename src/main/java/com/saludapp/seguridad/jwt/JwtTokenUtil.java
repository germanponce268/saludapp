package com.saludapp.seguridad.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.*;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saludapp.entidades.Usuario;

@Component
public class JwtTokenUtil implements Serializable {	
	
	private final static org.slf4j.Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
	@Value("${jwt.secret}")
	private String secret;
	
	public String getClaimFromToken(String token, Function<Claims, String> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Date getClaimFromTokenDate(String token, Function<Claims, Date> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public String generateToken(Usuario usuario) {
		Map<String, Object> claims = new HashMap<>();		
		return generateJwtToken(claims, usuario.getUsername());
	}
	
	public String generateJwtToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 5 *60*60 * 1000))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	
	public Boolean validateToken(String token, Usuario usuario) {
		final String username = getUsernameFromToken(token);
		return (username.equals(usuario.getUsername()) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	private Date getExpirationDateFromToken(String token) {

		return getClaimFromTokenDate(token, Claims::getExpiration);
	}

	
	
}
