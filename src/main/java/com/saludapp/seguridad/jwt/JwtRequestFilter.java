package com.saludapp.seguridad.jwt;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Decoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.saludapp.constantes.SecurityConstant;
import com.saludapp.servicios.UserDetailsServiceImpl;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getMethod().equalsIgnoreCase(SecurityConstant.OPTIONS_HTTP_METHOD)) {
			response.setStatus(HttpStatus.OK.value());
	}else{
		//Obtiene el token del header
		String authorizationHeader = request.getHeader(org.springframework.http.HttpHeaders.AUTHORIZATION);
		//valida si el token es nulo o no empieza con el prefijo Bearer
		if(authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}
		//retorna el token sin el prefijo Bearer
		String token = authorizationHeader.substring(SecurityConstant.TOKEN_PREFIX.length());
		//Obtiene el nombre de usuario del token
		String username = jwtTokenProvider.getSubject(token);
		//validacion del token y si el nombre de usuario no es nulo
		if(jwtTokenProvider.isTokenValid(username, token)){
			
			//Obtiene los roles del token
			List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
			//Obtiene el contexto de seguridad
			Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);
			//Setea el contexto de seguridad
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}else{
			//Si el token no es valido se limpia el contexto de seguridad
			SecurityContextHolder.clearContext();
		}
	}
	filterChain.doFilter(request, response);
	}
}

