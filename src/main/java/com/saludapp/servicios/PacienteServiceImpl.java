package com.saludapp.servicios;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.saludapp.entidades.Paciente;
import com.saludapp.repositorios.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class PacienteServiceImpl implements PacienteService {

	@Autowired
	private PacienteRepository pRepository;
	
	public List<Paciente> savePaciente(Paciente paciente) {
		this.pRepository.save(paciente);
		return (List<Paciente>) this.pRepository.findAll();
	}
	
	public List<Paciente> obtenerListaPacientes(){
	  return (List<Paciente>)this.pRepository.findAll();
	}
	public List<Paciente> deletePaciente(Integer pacienteId) {
		System.out.println(pacienteId);
		this.pRepository.deleteById(pacienteId);
		return (List<Paciente>) this.pRepository.findAll();
	}

	public List<Paciente> updatePaciente(Paciente paciente) {
		System.out.println(paciente);
		Paciente pacienteDB = this.pRepository.findById(paciente.getId()).get();
		pacienteDB = paciente;
		System.out.println(pacienteDB);
		this.pRepository.save(pacienteDB);
		return (List<Paciente>) this.pRepository.findAll() ;
	}

	public Queue<Paciente> savePacienteQueue(Paciente paciente) {
		
		this.pRepository.save(paciente);
		List<Paciente> listaDB = (List<Paciente>) this.pRepository.findAll();
		Queue<Paciente> listaQueue = new LinkedList<Paciente>(listaDB);
		return listaQueue;
	}
	

}
