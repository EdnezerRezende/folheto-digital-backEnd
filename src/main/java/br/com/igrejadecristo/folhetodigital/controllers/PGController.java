package br.com.igrejadecristo.folhetodigital.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	@CrossOrigin
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<PequenoGrupo>> findAll() {
		return ResponseEntity.ok().body(pgService.buscarTodos());
	}
	
	@CrossOrigin
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<PequenoGrupo>> findPorIgreja(@PathVariable Integer idIgreja) {
		return ResponseEntity.ok().body(pgService.buscarPorIgreja(idIgreja));
	}
	
	@CrossOrigin
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody PgNewDTO dto) {
		PequenoGrupo obj = pgService.salvar(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@CrossOrigin
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		pgService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@RequestMapping(value="/picture", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") MultipartFile file) {
		URI uri = pgService.uploadProfilePicture(file);
		return ResponseEntity.created(uri).build();
	}
}
