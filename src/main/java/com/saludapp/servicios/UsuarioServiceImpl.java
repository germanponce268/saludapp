package com.saludapp.servicios;

import java.util.Optional;

import com.saludapp.entidades.Role;
import com.saludapp.excepciones.EmailExistException;
import com.saludapp.excepciones.UsernameExistException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.saludapp.entidades.Usuario;
import com.saludapp.repositorios.UsuarioRepository;
import com.saludapp.seguridad.jwt.JwtTokenUtil;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario saveUsuario(Usuario usuario) {
		System.out.println("hasta aca llego, se grabo otro usuario");
		return this.usuarioRepository.save(usuario);
	
	}

	public Usuario registrarUsuario(String nombre, String apellido, String nombreUsuario, String email, String password) throws EmailExistException, UsernameExistException {
		Usuario usuario = new Usuario(nombre, apellido, nombreUsuario, email);
		usuario.setAvtive(true);
		usuario.setNotLocked(true);
		usuario.setRole(Role.ROLE_USER.name());
		usuario.setAuthorities(Role.ROLE_USER.getAuthorities());
		usuario.setPassword(password);
		validateNewUsernameAndEmail(StringUtils.EMPTY, nombreUsuario, email);
		usuario.setPassword(encodePassword(usuario.getPassword()));
		System.out.println("Se guardo un nuevo usuario: " + usuario);
		return this.usuarioRepository.save(usuario);
	}
	
	public Usuario findByUsername(String username) {
		Optional<Usuario> usuarioDB =  usuarioRepository.findByUsername(username);
		return   usuarioDB.get();
	}
	private Usuario validateNewUsernameAndEmail(String nombreUsuarioActual, String nuevoUsuario, String nuevoEmail) throws UsernameExistException, EmailExistException {
		if(StringUtils.isNotBlank(nombreUsuarioActual)){
			Optional<Usuario> usuarioActual = this.usuarioRepository.findByUsername(nombreUsuarioActual);
			if(usuarioActual.get() == null){
				throw new UsernameNotFoundException("No se encontro al nombre de usuario " + nombreUsuarioActual);
			}
			Optional<Usuario> usuarioDelUsuario = this.usuarioRepository.findByUsername(nuevoUsuario);
			if(usuarioDelUsuario.get() != null && usuarioActual.get().getId().equals(usuarioDelUsuario.get().getId())){
				throw new UsernameExistException("El usuario ya existe cabron");
			}
			return usuarioActual.get();
		}else{
			Optional<Usuario> usuarioDelUsuario = this.usuarioRepository.findByUsername(nuevoUsuario);
			if(usuarioDelUsuario.isPresent()){
				throw new UsernameExistException("El usuario ya existe");
			}
			Optional<Usuario> usuarioPorEmail = this.usuarioRepository.findUserByEmail(nuevoEmail);
			if(usuarioPorEmail.isPresent()){
				throw new EmailExistException("El email ya existe");
			}
			return null;
		}
	}

	private String encodePassword(String password){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}
}
