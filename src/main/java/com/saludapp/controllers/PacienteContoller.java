package com.saludapp.controllers;
import com.saludapp.repositorios.PacienteRepository;
import com.saludapp.entidades.Paciente;
import com.saludapp.servicios.PacienteService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/servicio")
public class PacienteContoller {
	
	@Autowired
	private PacienteService pService;
	@CrossOrigin
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
					value="/savePaciente")
	public List<Paciente> save(@RequestBody Paciente paciente) {
		return this.pService.savePaciente(paciente);
	}
	@CrossOrigin
	@GetMapping("/listaPacientes")
	public List<Paciente> listaPacientes() {
		return this.pService.obtenerListaPacientes();
	}
	
	@CrossOrigin
	@DeleteMapping("/eliminarPaciente/{id}")
	public List<Paciente> eliminarPaciente(@PathVariable("id") Integer pacienteId) {
		return this.pService.deletePaciente(pacienteId);
	}
	
	@CrossOrigin
	@PutMapping("/updatePaciente/{id}")
	public List<Paciente> actualizarPaciente(@RequestBody Paciente paciente){
		return this.pService.updatePaciente(paciente);
	}
	
	@CrossOrigin
	@PostMapping("/savePacienteQueue")
	public ResponseEntity<Queue<Paciente>> saveQueued(@RequestBody Paciente paciente){
		return  new ResponseEntity<Queue<Paciente>>(this.pService.savePacienteQueue(paciente), HttpStatus.OK);
	
	}
	
}
