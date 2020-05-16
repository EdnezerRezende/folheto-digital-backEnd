package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	
	@PreAuthorize("hasAnyRole('ADMIN','MEMBRO')") 
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Devocional>> findAll() {
		return ResponseEntity.ok().body(devocionalService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MEMBRO')") 
	@RequestMapping(value="/igreja/{idIgreja}/{idMembro}", method = RequestMethod.GET)
	public ResponseEntity<List<Devocional>> findPorIgreja(@PathVariable Integer idIgreja,@PathVariable Integer idMembro) {
		return ResponseEntity.ok().body(devocionalService.buscarPorIgreja(idIgreja, idMembro));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody DevocionalNewDTO dto) {
		Devocional obj = devocionalService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		devocionalService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
