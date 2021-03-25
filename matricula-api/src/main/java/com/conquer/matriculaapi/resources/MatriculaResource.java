package com.conquer.matriculaapi.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.conquer.matriculaapi.MatriculaApiDto;
import com.conquer.matriculaapi.service.MatriculaService;

@RestController
public class MatriculaResource {

	@Autowired
	private MatriculaService matriculaService;
	
	@PostMapping(path = "/registrar", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> registrarMatricula(@RequestBody MatriculaApiDto matricula ){
		
		return this.matriculaService.registrarMatricula(matricula);
		
	}
	
	@GetMapping(value = "/consultar", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> consultarMatricula(@RequestParam("codMatricula") String codigoMatricula){
		
		return this.matriculaService.consultarMatricula(codigoMatricula);
	}
}
