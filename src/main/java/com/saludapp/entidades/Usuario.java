package com.saludapp.entidades;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@JsonIgnoreProperties
@Table(name="usuarios", schema="pacientes")
public class Usuario {
	@Id
	@Column(name="usuario_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private String password;
	private String email;
	private String nombre;
	private String apellido;
	private String role;
	@Column(name="authorities")
	private String[] authorities;
	private boolean isAvtive;
	private boolean isNotLocked;

	public Usuario() {

	}

	public Usuario(Integer id, String username, String password, String email, String nombre, String apellido, String role, String[] authorities, boolean isAvtive, boolean isNotLocked) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.nombre = nombre;
		this.apellido = apellido;
		this.role = role;
		this.authorities = authorities;
		this.isAvtive = isAvtive;
		this.isNotLocked = isNotLocked;
	}

	public Usuario(String nombre, String apellido, String username, String email) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.username = username;
		this.email = email;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String[] getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String roles) {
		this.role = roles;
	}

	public boolean isAvtive() {

		return isAvtive;
	}

	public void setAvtive(boolean avtive) {
		isAvtive = avtive;
	}

	public boolean isNotLocked() {
		return isNotLocked;
	}

	public void setNotLocked(boolean notLocked) {
		isNotLocked = notLocked;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}




}
