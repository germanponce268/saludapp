package com.saludapp.servicios;

import org.springframework.beans.factory.annotation.Autowired;

import com.saludapp.entidades.Paciente;
import com.saludapp.repositorios.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {

	@Autowired
	private PacienteRepository pRepository;
	
	public void savePaciente(Paciente paciente) {
		pRepository.save(paciente);
	}
	
	
}
