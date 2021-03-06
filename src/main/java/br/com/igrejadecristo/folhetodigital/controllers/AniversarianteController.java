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

import br.com.igrejadecristo.folhetodigital.dto.AniversarianteAlteraDTO;
import br.com.igrejadecristo.folhetodigital.dto.AniversarianteInfoDTO;
import br.com.igrejadecristo.folhetodigital.dto.AniversarianteNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Aniversariante;
import br.com.igrejadecristo.folhetodigital.services.AniversarianteService;

@RestController
@RequestMapping(value="/aniversariantes")
public class AniversarianteController {

	@Autowired
	private AniversarianteService aniversarianteService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')") 
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscaPorId(@PathVariable Integer id){
		Aniversariante aniversariante = aniversarianteService.buscar(id);
		
		return ResponseEntity.ok(aniversariante);
	}

	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AniversarianteNewDTO objDto) {
		Aniversariante obj = aniversarianteService.fromDTO(objDto);
		obj = aniversarianteService.salvarAniversariante(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateAniversariante(@RequestBody AniversarianteAlteraDTO objDto,@PathVariable Integer id) {
		aniversarianteService.updateDados(objDto, id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		aniversarianteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')") 
	@RequestMapping(value="/igreja/{idIgreja}", method=RequestMethod.GET)
	public ResponseEntity<List<AniversarianteInfoDTO>> findAll(@PathVariable Integer idIgreja) {
		List<Aniversariante> list = aniversarianteService.buscarAniversariantesPorIdIgreja(idIgreja);
		List<AniversarianteInfoDTO> listDto = list.stream().map(obj -> new AniversarianteInfoDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
}
