package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import br.com.igrejadecristo.folhetodigital.dto.MensagemDTO;
import br.com.igrejadecristo.folhetodigital.dto.MensagemNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.services.MensagemService;

@RestController
@RequestMapping(value="/mensagens")
public class MensagemController {

	@Autowired
	private MensagemService mensagemService;
	
	@PreAuthorize("hasAnyRole('ADMIN','MEMBRO')") 
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MensagemDTO>> findAll() {
		List<Mensagem> list = mensagemService.buscarTodos();
		List<MensagemDTO> listDto = list.stream().map(obj -> new MensagemDTO(obj)).sorted().collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> saveMensagem(@Valid @RequestBody MensagemNewDTO dto) {
		Mensagem obj = mensagemService.salvarMensagem(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping(path="/{idMensagem}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaMensagem(@PathVariable Integer idMensagem) {
		mensagemService.deletarMensagem(idMensagem);
		return ResponseEntity.noContent().build();
	}
	
}
