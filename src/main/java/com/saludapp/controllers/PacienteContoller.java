package com.saludapp.controllers;
import com.saludapp.repositorios.PacienteRepository;
import com.saludapp.entidades.Paciente;
import com.saludapp.servicios.PacienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/servicio")
public class PacienteContoller {
	
	@Autowired
	private PacienteService pService;
	
	@GetMapping("/savePaciente")
	public void save(Paciente paciente) {
		Paciente pacienteAux = new Paciente();
		pacienteAux.setNombre("Barulo");
		pacienteAux.setApellido("Costar");
		pacienteAux.setDni(27845989L);
		pacienteAux.setId(1);
		paciente = pacienteAux;
		System.out.println(paciente.getNombre());
		pService.savePaciente(paciente);
	}
}
