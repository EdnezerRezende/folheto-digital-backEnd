package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.igrejadecristo.folhetodigital.dto.MembroDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.services.MembroService;

@RestController
@RequestMapping(value="/membro")
public class MembroController {

	@Autowired
	private MembroService membroService;
	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscaPorId(@PathVariable Integer id){
		Membro membro = membroService.buscar(id);
		
		return ResponseEntity.ok(membro);
	}

	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<Membro> find(@RequestParam(value="value") String email) {
		Membro obj = membroService.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	

//	@PreAuthorize("hasAnyRole('ADMIN','CHEFE_GESTOR')")
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody MembroNewDTO objDto) {
		Membro obj = membroService.fromDTO(objDto);
		obj = membroService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN','CHEFE_GESTOR')")	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody MembroDTO objDto, @PathVariable Integer id) {
		Membro obj = membroService.fromDTO(objDto);
		obj.setId(id);
		obj = membroService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		membroService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<MembroDTO>> findAll() {
		List<Membro> list = membroService.findAll();
		List<MembroDTO> listDto = list.stream().map(obj -> new MembroDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
//	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<MembroDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {
		Page<Membro> list = membroService.findPage(page, linesPerPage, orderBy, direction);
		Page<MembroDTO> listDto = list.map(obj -> new MembroDTO(obj));  
		return ResponseEntity.ok().body(listDto);
	}	
}
