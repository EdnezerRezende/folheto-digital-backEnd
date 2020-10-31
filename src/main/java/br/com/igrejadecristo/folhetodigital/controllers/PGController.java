package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.igrejadecristo.folhetodigital.dto.PgNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;
import br.com.igrejadecristo.folhetodigital.services.PGService;

@RestController
@RequestMapping(value="/pgs")
public class PGController {

	@Autowired
	private PGService pgService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')") 
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<PequenoGrupo>> findAll() {
		return ResponseEntity.ok().body(pgService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')") 
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<PequenoGrupo>> findPorIgreja(@PathVariable Integer idIgreja) {
		return ResponseEntity.ok().body(pgService.buscarPorIgreja(idIgreja));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody PgNewDTO dto) {
		PequenoGrupo obj = pgService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		pgService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')") 
	@RequestMapping(value="/picture/{idPg}", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file,
			@PathVariable Integer idPg) {
		URI uri = pgService.uploadProfilePicture(file, idPg);
		return ResponseEntity.created(uri).build();
	}
	
}
