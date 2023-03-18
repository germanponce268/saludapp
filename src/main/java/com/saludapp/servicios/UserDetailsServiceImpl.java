package com.saludapp.servicios;

import com.saludapp.entidades.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saludapp.entidades.SecurityUser;
import com.saludapp.repositorios.PacienteRepository;
import com.saludapp.repositorios.UsuarioRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	UsuarioRepository uRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user  = Optional.ofNullable(uRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado" + username)
				));
		Usuario userChecked = user.get();

		return new User(userChecked.getUsername(), userChecked.getPassword(), new ArrayList<>());
	}
	
	

}
