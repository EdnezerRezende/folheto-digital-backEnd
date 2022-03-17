package br.com.pic.folheto.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.pic.folheto.dto.MissaoDTO;
import br.com.pic.folheto.dto.MissaoNewDTO;
import br.com.pic.folheto.entidades.Missao;
import br.com.pic.folheto.services.MissaoService;

@RestController
@RequestMapping(value="/missoes")
public class MissaoController {

	@Autowired
	private MissaoService missaoService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todas as missões", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MissaoDTO>> findAll() {
		List<Missao> list = missaoService.buscarTodos();
		List<MissaoDTO> listDto = list.stream().map(obj -> new MissaoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Salvar missão", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> saveMissao(@Valid @RequestBody MissaoNewDTO dto) {
		Missao obj = missaoService.salvarMissao(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Deletar missão", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{idMissao}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaMissao(@PathVariable Integer idMissao) {
		missaoService.deletarMissao(idMissao);
		return ResponseEntity.noContent().build();
	}
	
}
