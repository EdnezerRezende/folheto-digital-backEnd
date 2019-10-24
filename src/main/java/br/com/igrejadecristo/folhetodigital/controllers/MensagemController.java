package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MensagemDTO>> findAll() {
		List<Mensagem> list = mensagemService.buscarTodos();
		List<MensagemDTO> listDto = list.stream().map(obj -> new MensagemDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@CrossOrigin
	@RequestMapping(value="/folheto/{idFolheto}", method = RequestMethod.GET)
	public ResponseEntity<MensagemDTO> findIdFolheto(@PathVariable Integer idFolheto) {
		return ResponseEntity.ok().body(mensagemService.buscarPorFolheto(idFolheto));
	}
	
	@CrossOrigin
	@RequestMapping(value="/{idMensagem}", method = RequestMethod.GET)
	public ResponseEntity<MensagemDTO> findIdMensagem(@PathVariable Integer idMensagem) {
		return ResponseEntity.ok().body(mensagemService.buscarPorFolheto(idMensagem));
	}
	
	@CrossOrigin
	@RequestMapping(value="/folheto", method = RequestMethod.POST)
	public ResponseEntity<Void> saveIdFolheto(@PathVariable MensagemNewDTO dto) {
		Mensagem obj = mensagemService.salvarMensagem(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
}
