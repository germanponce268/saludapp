package com.saludapp.controllers;

import com.saludapp.excepciones.EmailExistException;
import com.saludapp.excepciones.UsernameExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saludapp.constantes.SecurityConstant;
import com.saludapp.entidades.SecurityUser;
import com.saludapp.entidades.Usuario;
import com.saludapp.seguridad.jwt.JwtTokenProvider;
import com.saludapp.servicios.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@PostMapping("/saveUsuario")
	public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario){
		System.out.println("El usuario" + usuario.toString());
		return ResponseEntity.ok().body(this.usuarioService.saveUsuario(usuario));
		
		
	}
	@CrossOrigin
	@PostMapping("/registrar")
	public ResponseEntity registrarUsuario(@RequestBody Usuario usuario) throws EmailExistException, UsernameExistException {
		Usuario user = this.usuarioService.registrarUsuario(usuario.getNombre(), usuario.getApellido(), usuario.getUsername(), usuario.getEmail(), usuario.getPassword());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	@PostMapping("/createToken")
	public ResponseEntity createToken(@RequestBody Usuario usuario){
		String token = this.jwtTokenProvider.generateToken(usuario);
		return ResponseEntity.ok().header(SecurityConstant.JWT_TOKEN_HEADER, token).body(token);
	}

	@CrossOrigin
	@PostMapping("/login2")
	public ResponseEntity login2(@RequestBody Usuario usuario){
		authenticate(usuario.getUsername(), usuario.getPassword());
		UserDetails userlogin = this.userDetailsService.loadUserByUsername(usuario.getUsername());
		System.out.println(userlogin.toString());
		HttpHeaders jwtHeader = getJwtHeader(usuario);
		return new ResponseEntity<>(userlogin,jwtHeader, HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Usuario usuario){		
		authenticate(usuario.getUsername(), usuario.getPassword());
		Usuario loginUser = this.usuarioService.findByUsername(usuario.getUsername());
		SecurityUser securityUser = new SecurityUser(loginUser);
		org.springframework.http.HttpHeaders jwtHeader = getJwtHeader(securityUser.getUser());
		return ResponseEntity.ok().headers(jwtHeader).body(loginUser);
	}

	private void authenticate(String usuario, String password){
		this.authManager.authenticate(new UsernamePasswordAuthenticationToken(usuario, password));
	}

	private org.springframework.http.HttpHeaders getJwtHeader(Usuario usuario){
		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateToken(usuario));
		return headers;
	}
	@CrossOrigin
	@PostMapping("/encodePassword")
	public ResponseEntity<String> encodePassword (@RequestBody Usuario usuario){
		return ResponseEntity.ok().body(new BCryptPasswordEncoder().encode(usuario.getPassword()));
	}


 }
