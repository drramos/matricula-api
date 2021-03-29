package com.conquer.matriculaapi.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.conquer.conquerutils.dto.MatriculaDto;
import com.conquer.conquerutils.dto.MensagemRetorno;
import com.conquer.conquerutils.dto.TurmaDto;
import com.conquer.conquerutils.entidades.Matricula;
import com.conquer.conquerutils.entidades.Turma;
import com.conquer.conquerutils.utils.UtilHttp;
import com.conquer.matriculaapi.MatriculaApiDto;
import com.google.gson.Gson;

@Service
public class MatriculaService {

	public ResponseEntity<?> registrarMatricula(MatriculaApiDto matricula){
		
//		1 - Aqui será feita a chamada para a camada de negócios, que utilizará 
//			os dados do DTO para buscar as informaçõeos na base, a partir das 
//			chamadas para as outras APIs
//		2 - Será levado em conta o tipo de curso a ser matrículado, caso seja digital
//			a turma não será informada, caso seja presencial, sim.
		
		Matricula matriculaEntidade = new Matricula();

		Turma turma = this.consultarTurma(matricula.getCodigoTurma());
		if(turma == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Turma informada não foi encontrada");
		}
		matriculaEntidade.setTurma(turma);
		
		
		
		return ResponseEntity.ok(matriculaEntidade);
	}
	
	public ResponseEntity<?> consultarMatricula(String codigoMatricula){
		
		MatriculaDto matriculaRetorno = new MatriculaDto();
//		Com base no código informado, será feita a consulta no banco montando o DTO de retorno com as informações
		
		return ResponseEntity.ok().body(matriculaRetorno);
	}
	
	public Turma consultarTurma(String codigoTurma) {
		
		String urlBase = "http://localhost:8085/turma-api/consultar";
		HashMap<String, String> parametros = new HashMap<>(); 
		parametros.put("codigoTurma", codigoTurma);
		String url = UtilHttp.montarURl(urlBase,parametros);
		
		MensagemRetorno mensagemRetorno = new MensagemRetorno();
		
		try {
			
			mensagemRetorno = UtilHttp.enviarGet(url);
			
			Gson gson = new Gson();
            TurmaDto turmaDto = new TurmaDto();
            turmaDto = gson.fromJson(mensagemRetorno.getMensagem(),TurmaDto.class);
            
			Turma turma;
			
			if(turmaDto != null) {
				ModelMapper modelMapper = new ModelMapper();
				turma = modelMapper.map(turmaDto, Turma.class);
				return turma;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
}
