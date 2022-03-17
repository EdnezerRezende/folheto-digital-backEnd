package br.com.pic.folheto.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pic.folheto.dto.OfertaServicoDTO;
import br.com.pic.folheto.dto.OfertaServicoNewDTO;
import br.com.pic.folheto.entidades.OfertaServico;
import br.com.pic.folheto.services.OfertaServicoService;

@RestController
@RequestMapping(value="/ofertas")
public class OfertaServicoController {

	@Autowired
	private OfertaServicoService ofertaServicoService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todas as ofertas e serviços", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<OfertaServicoDTO>> findAll() {
		return ResponseEntity.ok().body(ofertaServicoService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todas as ofertas e serviços por id da igreja", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<OfertaServico>> findPorIgreja(@PathVariable Integer idIgreja) {
		return ResponseEntity.ok().body(ofertaServicoService.buscarPorIgreja(idIgreja));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Salvar oferta ou serviço", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody OfertaServicoNewDTO dto) {
		OfertaServico obj = ofertaServicoService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Deletar oferta ou serviço", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		ofertaServicoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
