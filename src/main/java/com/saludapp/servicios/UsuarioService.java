package com.saludapp.servicios;

import com.saludapp.entidades.Usuario;
import com.saludapp.excepciones.EmailExistException;
import com.saludapp.excepciones.UsernameExistException;

public interface UsuarioService {

	public Usuario saveUsuario(Usuario usuario);
	
	public Usuario findByUsername(String username);

	public Usuario registrarUsuario(String nombre, String apellido, String nombreUsuario, String email, String password) throws EmailExistException, UsernameExistException;

}
