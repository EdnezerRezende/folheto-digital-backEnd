package br.com.igrejadecristo.folhetodigital.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.igrejadecristo.folhetodigital.dto.FolhetoDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Folheto;
import br.com.igrejadecristo.folhetodigital.services.FolhetoService;

@RestController
@RequestMapping(value="/folhetos")
public class FolhetoController {

	@Autowired
	private FolhetoService folhetoService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<FolhetoDTO>> findAll() {
		List<Folheto> list = folhetoService.buscarTodos();
		List<FolhetoDTO> listDto = list.stream().map(obj -> new FolhetoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
