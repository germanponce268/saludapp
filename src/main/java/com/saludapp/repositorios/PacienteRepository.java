package com.saludapp.repositorios;
import org.springframework.data.repository.CrudRepository;

import com.saludapp.entidades.Paciente;

public interface PacienteRepository extends CrudRepository<Paciente, Integer>  {

}
