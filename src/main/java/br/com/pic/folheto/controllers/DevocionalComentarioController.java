package br.com.pic.folheto.controllers;

import java.net.URI;

import javax.validation.Valid;

import br.com.pic.folheto.services.DevocionalComentarioService;
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

import br.com.pic.folheto.dto.DevocionalComentarioNewDTO;
import br.com.pic.folheto.entidades.DevocionalComentario;

@RestController
@RequestMapping(value="/comentarios")
public class DevocionalComentarioController {

	@Autowired
	private DevocionalComentarioService devocionalComentarioService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar comentários por referencia e membro", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{idMembro}/{idReferencia}", method=RequestMethod.GET)
	public ResponseEntity<DevocionalComentario> findByReferenciaIdAndMembro(@PathVariable final Integer idMembro, @PathVariable final Integer idReferencia) {
		return ResponseEntity.ok().body(devocionalComentarioService.buscarPorReferenciaEMembro(idMembro, idReferencia));
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO')")
	@Operation(summary = "Salvar comentários", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody final DevocionalComentarioNewDTO dto) {
		final DevocionalComentario obj = devocionalComentarioService.salvar(dto);
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO')")
	@Operation(summary = "Deletar comentário", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable final Integer id) {
		devocionalComentarioService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
