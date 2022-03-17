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

import br.com.pic.folheto.dto.MensagemDTO;
import br.com.pic.folheto.dto.MensagemNewDTO;
import br.com.pic.folheto.entidades.Mensagem;
import br.com.pic.folheto.services.MensagemService;

@RestController
@RequestMapping(value="/mensagens")
public class MensagemController {

	@Autowired
	private MensagemService mensagemService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todas as mensagens", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MensagemDTO>> findAll() {
		List<Mensagem> list = mensagemService.buscarTodos();
		List<MensagemDTO> listDto = list.stream().map(obj -> new MensagemDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Salvar mensagem", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> saveMensagem(@Valid @RequestBody MensagemNewDTO dto) {
		Mensagem obj = mensagemService.salvarMensagem(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Deletar mensagem", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{idMensagem}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletaMensagem(@PathVariable Integer idMensagem) {
		mensagemService.deletarMensagem(idMensagem);
		return ResponseEntity.noContent().build();
	}
	
}
