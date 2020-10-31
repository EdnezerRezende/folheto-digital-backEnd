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

import br.com.igrejadecristo.folhetodigital.dto.AgendaEventoNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.AgendaEvento;
import br.com.igrejadecristo.folhetodigital.services.AgendaEventoService;

@RestController
@RequestMapping(value="/eventosAgendas")
public class AgendaEventoController {

	@Autowired
	private AgendaEventoService agendaEventoService;
	
	@PreAuthorize("hasAnyRole(''MEMBRO','VISITANTE')") 
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AgendaEvento>> findAll() {
		return ResponseEntity.ok().body(agendaEventoService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')") 
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<AgendaEvento>> findPorIgreja(@PathVariable Integer idIgreja) {
		return ResponseEntity.ok().body(agendaEventoService.buscarPorIgreja(idIgreja));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody AgendaEventoNewDTO dto) {
		AgendaEvento obj = agendaEventoService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		agendaEventoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
