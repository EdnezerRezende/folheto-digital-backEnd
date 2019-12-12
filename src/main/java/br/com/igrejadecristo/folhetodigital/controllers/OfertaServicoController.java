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

import br.com.igrejadecristo.folhetodigital.dto.OfertaServicoDTO;
import br.com.igrejadecristo.folhetodigital.dto.OfertaServicoNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.OfertaServico;
import br.com.igrejadecristo.folhetodigital.services.OfertaServicoService;

@RestController
@RequestMapping(value="/ofertas")
public class OfertaServicoController {

	@Autowired
	private OfertaServicoService ofertaServicoService;
	
	@PreAuthorize("hasAnyRole('ADMIN','MEMBRO')") 
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<OfertaServicoDTO>> findAll() {
		return ResponseEntity.ok().body(ofertaServicoService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','MEMBRO')") 
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaServico>> findPorIgreja(@PathVariable Integer idIgreja) {
		return ResponseEntity.ok().body(ofertaServicoService.buscarPorIgreja(idIgreja));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody OfertaServicoNewDTO dto) {
		OfertaServico obj = ofertaServicoService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		ofertaServicoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
