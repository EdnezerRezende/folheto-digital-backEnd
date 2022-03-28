package br.com.pic.folheto.controllers;

import br.com.pic.folheto.dto.AniversarianteAlteraDTO;
import br.com.pic.folheto.dto.AniversarianteInfoDTO;
import br.com.pic.folheto.dto.AniversarianteNewDTO;
import br.com.pic.folheto.entidades.Aniversariante;
import br.com.pic.folheto.services.AniversarianteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/aniversariantes")
public class AniversarianteController {

	@Autowired
	private AniversarianteService aniversarianteService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar aniversariante pelo ID", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscaPorId(final @PathVariable Integer id){
		final Aniversariante aniversariante = aniversarianteService.buscar(id);
		
		return ResponseEntity.ok(aniversariante);
	}

	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Salvar aniversariante ", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(final @Valid @RequestBody AniversarianteNewDTO objDto) {
		Aniversariante obj = aniversarianteService.fromDTO(objDto);
		obj = aniversarianteService.salvarAniversariante(obj);
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Atualizar aniversariante", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateAniversariante(final @RequestBody AniversarianteAlteraDTO objDto,
													 final @PathVariable Integer id) {
		aniversarianteService.updateDados(objDto, id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Deletar aniversariante pelo ID", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(final @PathVariable Integer id) {
		aniversarianteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Listar todos aniversariantes por igreja", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/igreja/{idIgreja}", method=RequestMethod.GET)
	public ResponseEntity<List<AniversarianteInfoDTO>> findAll(final @PathVariable Integer idIgreja) {
		final List<Aniversariante> list = aniversarianteService.buscarAniversariantesPorIdIgreja(idIgreja);
		final List<AniversarianteInfoDTO> listDto = list.stream().map(obj -> AniversarianteInfoDTO.builder()
													.nome(obj.getNome())
													.email(obj.getEmail())
													.dataNascimento(obj.getDataNascimento().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
													.build()
												).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);
	}
	
}
