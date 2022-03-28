package br.com.pic.folheto.controllers;

import br.com.pic.folheto.dto.AgendaEventoNewDTO;
import br.com.pic.folheto.entidades.AgendaEvento;
import br.com.pic.folheto.services.AgendaEventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/eventosAgendas")
public class AgendaEventoController {

	@Autowired
	private AgendaEventoService agendaEventoService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Obter toda agenda", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<AgendaEvento>> findAll() {
		return ResponseEntity.ok().body(agendaEventoService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Obter agenda por igreja", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<AgendaEvento>> findPorIgreja(final @PathVariable Integer idIgreja) {
		return ResponseEntity.ok().body(agendaEventoService.buscarPorIgreja(idIgreja));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Salvar uma agenda", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(final @Valid @RequestBody AgendaEventoNewDTO dto) {
		AgendaEvento obj = agendaEventoService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Deletar um item da agenda", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(final @PathVariable Integer id) {
		agendaEventoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
