package br.com.igrejadecristo.folhetodigital.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.igrejadecristo.folhetodigital.dto.IgrejaDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.services.IgrejaService;

@RestController
@RequestMapping(value="/igrejas")
public class IgrejaController {

	@Autowired
	private IgrejaService igrejaService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<IgrejaDTO>> findAll() {
		List<Igreja> list = igrejaService.buscarTodos();
		List<IgrejaDTO> listDto = list.stream().map(obj -> new IgrejaDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
