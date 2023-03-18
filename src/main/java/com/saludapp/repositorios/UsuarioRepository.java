package com.saludapp.repositorios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.saludapp.entidades.Usuario;
import org.springframework.stereotype.Repository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

	Optional<Usuario> findByUsername(String username);

	Optional<Usuario>findUserByEmail(String email);
}
