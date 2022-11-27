package com.saludapp.servicios;

import java.util.List;
import java.util.Queue;

import com.saludapp.entidades.Paciente;



public interface PacienteService {

	public List<Paciente> savePaciente(Paciente paciente);
	
	public List<Paciente> obtenerListaPacientes();
	
	public List<Paciente> deletePaciente(Integer paciente);

	public List<Paciente> updatePaciente(Paciente pacienteId);

	public Queue<Paciente> savePacienteQueue(Paciente paciente);
	
}
