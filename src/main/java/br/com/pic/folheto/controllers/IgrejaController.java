package br.com.pic.folheto.controllers;

import br.com.pic.folheto.dto.IgrejaDTO;
import br.com.pic.folheto.dto.IgrejaInfoDTO;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.services.IgrejaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/igrejas")
public class IgrejaController {

	@Autowired
	private IgrejaService igrejaService;
	
	@RequestMapping(method=RequestMethod.GET)
	@Operation(summary = "Buscar todas igrejas", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<List<IgrejaDTO>> findAll() {
		final List<Igreja> list = igrejaService.buscarTodos();
		final List<IgrejaDTO> listDto = list.stream().map(obj -> IgrejaDTO.builder()
														.id(obj.getId())
														.nome(obj.getNome())
														.endereco(obj.getEndereco())
														.membros(obj.getMembros())
														.build()).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar informações da igreja por id", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<IgrejaInfoDTO> findById(@PathVariable final Integer id) {
		return ResponseEntity.ok().body(igrejaService.findById(id));
	}
	
}
