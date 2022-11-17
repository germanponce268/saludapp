package com.saludapp.controllers;
import com.saludapp.repositorios.PacienteRepository;
import com.saludapp.entidades.Paciente;
import com.saludapp.servicios.PacienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/servicio")
public class PacienteContoller {
	
	@Autowired
	private PacienteService pService;
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces=MediaType.APPLICATION_JSON_VALUE,
					value="/savePaciente")
	public ResponseEntity save(@RequestBody Paciente paciente) {
		System.out.println(paciente);
		//pService.savePaciente(paciente);
		pService.savePaciente(paciente);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
