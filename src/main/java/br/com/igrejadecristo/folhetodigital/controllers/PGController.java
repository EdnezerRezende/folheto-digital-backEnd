package br.com.igrejadecristo.folhetodigital.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

	
}
