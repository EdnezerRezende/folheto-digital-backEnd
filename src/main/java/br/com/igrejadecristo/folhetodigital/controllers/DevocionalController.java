package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.igrejadecristo.folhetodigital.dto.DevocionalNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Devocional;
import br.com.igrejadecristo.folhetodigital.services.DevocionalService;

@RestController
@RequestMapping(value="/devocionais")
public class DevocionalController {

	@Autowired
	private DevocionalService devocionalService;
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Devocional>> findAll() {
		return ResponseEntity.ok().body(devocionalService.buscarTodos());
	}
	
	@CrossOrigin
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<Devocional>> findPorIgreja(@PathVariable Integer idIgreja) {
		return ResponseEntity.ok().body(devocionalService.buscarPorIgreja(idIgreja));
	}
	
	@CrossOrigin
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody DevocionalNewDTO dto) {
		Devocional obj = devocionalService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		devocionalService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
