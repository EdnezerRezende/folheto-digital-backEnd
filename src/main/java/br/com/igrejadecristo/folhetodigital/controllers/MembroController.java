package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.igrejadecristo.folhetodigital.dto.MembroAlteraDadosDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroAlteraPerfilDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroInfoDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.services.MembroService;

@RestController
@RequestMapping(value="/membros")
public class MembroController {

	@Autowired
	private MembroService membroService;
	
	@PreAuthorize("hasAnyRole('ADMIN','MEMBRO')") 
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscaPorId(@PathVariable Integer id){
		Membro membro = membroService.buscar(id);
		
		return ResponseEntity.ok(membro);
	}

	@PreAuthorize("hasAnyRole('ADMIN','MEMBRO')") 
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<MembroInfoDTO> find(@RequestParam(value="value") String email) {
		MembroInfoDTO obj = membroService.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody MembroNewDTO objDto) {
		Membro obj = membroService.fromDTO(objDto);
		obj = membroService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/perfil", method=RequestMethod.PUT)
	public ResponseEntity<Void> updatePerfil(@RequestBody MembroAlteraPerfilDTO objDto) {
		membroService.update(objDto);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> updateMembro(@RequestBody MembroAlteraDadosDTO objDto,@PathVariable Integer id) {
		membroService.updateDados(objDto, id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		membroService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
	@RequestMapping(value="/igreja/{idIgreja}", method=RequestMethod.GET)
	public ResponseEntity<List<MembroDTO>> findAll(@PathVariable Integer idIgreja) {
		List<Membro> list = membroService.findAll(idIgreja);
		List<MembroDTO> listDto = list.stream().map(obj -> new MembroDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')") 
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
	
	@PreAuthorize("hasAnyRole('MEMBRO')") 
	@RequestMapping(value="/aniversariantes/{idIgreja}", method=RequestMethod.GET)
	public ResponseEntity<List<MembroDTO>> buscarTodosAniversariantes(@PathVariable Integer idIgreja) {
		List<Membro> list = membroService.findAllMembrosByDataNascimento(idIgreja);
		List<MembroDTO> listDto = list.stream().map(obj -> new MembroDTO(obj)).collect(Collectors.toList());  
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/picture/{idMembro}", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file,
			@PathVariable Integer idMembro) {
		URI uri = membroService.uploadProfilePicture(file, idMembro);
		return ResponseEntity.created(uri).build();
	}
}
