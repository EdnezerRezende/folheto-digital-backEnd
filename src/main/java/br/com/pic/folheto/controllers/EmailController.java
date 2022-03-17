package br.com.pic.folheto.controllers;

import br.com.pic.folheto.dto.ContatoDTO;
import br.com.pic.folheto.services.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/emails")
public class EmailController {

	@Autowired
	private ContatoService contatoService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Enviar email de contato", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/contato", method = RequestMethod.POST)
	public ResponseEntity<Void> enviarContato(@RequestBody ContatoDTO dto) {
		contatoService.enviarContato(dto);
		return ResponseEntity.ok().build();
	}
	
}
