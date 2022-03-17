package br.com.pic.folheto.controllers;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.pic.folheto.dto.CidadeDTO;
import br.com.pic.folheto.dto.EstadoDTO;
import br.com.pic.folheto.entidades.Cidade;
import br.com.pic.folheto.entidades.Estado;
import br.com.pic.folheto.services.CidadeService;
import br.com.pic.folheto.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;
	
	
	@Autowired
	private CidadeService cidadeService;
	
	@RequestMapping(method=RequestMethod.GET)
	@Operation(summary = "Buscar todos os estados", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = estadoService.buscarTodos();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/{estadoId}/cidades", method = RequestMethod.GET)
	@Operation(summary = "Buscar todos as cidades", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<List<CidadeDTO>> findAll(@PathVariable Integer estadoId) {
		List<Cidade> list = cidadeService.findByEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
}
