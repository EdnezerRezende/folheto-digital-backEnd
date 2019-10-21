package br.com.igrejadecristo.folhetodigital.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.igrejadecristo.folhetodigital.dto.MensagemDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.services.MensagemService;

@RestController
@RequestMapping(value="/mensagens")
public class MensagemController {

	@Autowired
	private MensagemService mensagemService;
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MensagemDTO>> findAll() {
		List<Mensagem> list = mensagemService.buscarTodos();
		List<MensagemDTO> listDto = list.stream().map(obj -> new MensagemDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/folheto/{idFolheto}", method = RequestMethod.GET)
	public ResponseEntity<MensagemDTO> findIdFolheto(@PathVariable Integer idFolheto) {
		mensagemService.buscarPorFolheto(idFolheto);
		return ResponseEntity.ok().body(mensagemService.buscarPorFolheto(idFolheto));
	}
	
}
