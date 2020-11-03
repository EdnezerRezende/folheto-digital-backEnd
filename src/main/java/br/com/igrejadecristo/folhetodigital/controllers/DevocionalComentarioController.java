package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;

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

import br.com.igrejadecristo.folhetodigital.dto.DevocionalComentarioNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.DevocionalComentario;
import br.com.igrejadecristo.folhetodigital.services.DevocionalComentarioService;

@RestController
@RequestMapping(value="/comentarios")
public class DevocionalComentarioController {

	@Autowired
	private DevocionalComentarioService devocionalComentarioService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')") 
	@RequestMapping(path="/{idMembro}/{idReferencia}", method=RequestMethod.GET)
	public ResponseEntity<DevocionalComentario> findByReferenciaIdAndMembro(@PathVariable Integer idMembro, @PathVariable Integer idReferencia) {
		return ResponseEntity.ok().body(devocionalComentarioService.buscarPorReferenciaEMembro(idMembro, idReferencia));
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO')") 
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody DevocionalComentarioNewDTO dto) {
		DevocionalComentario obj = devocionalComentarioService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO')") 
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		devocionalComentarioService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	
}
