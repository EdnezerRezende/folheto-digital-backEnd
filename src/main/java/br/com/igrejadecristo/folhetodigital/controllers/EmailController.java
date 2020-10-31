package br.com.igrejadecristo.folhetodigital.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.igrejadecristo.folhetodigital.dto.ContatoDTO;
import br.com.igrejadecristo.folhetodigital.services.ContatoService;

@RestController
@RequestMapping(value="/emails")
public class EmailController {

	@Autowired
	private ContatoService contatoService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')") 
	@RequestMapping(path="/contato", method = RequestMethod.POST)
	public ResponseEntity<Void> enviarContato(@RequestBody ContatoDTO dto) {
		contatoService.enviarContato(dto);
		return ResponseEntity.ok().build();
	}
	
}
