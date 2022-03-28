package br.com.pic.folheto.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import br.com.pic.folheto.dto.PgNewDTO;
import br.com.pic.folheto.entidades.PequenoGrupo;
import br.com.pic.folheto.services.PGService;

@RestController
@RequestMapping(value="/pgs")
public class PGController {

	@Autowired
	private PGService pgService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todos Pg´s", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<PequenoGrupo>> findAll() {
		return ResponseEntity.ok().body(pgService.buscarTodos());
	}
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todos Pg´s por id da igreja", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/igreja/{idIgreja}", method = RequestMethod.GET)
	public ResponseEntity<List<PequenoGrupo>> findPorIgreja(@PathVariable final Integer idIgreja) {
		return ResponseEntity.ok().body(pgService.buscarPorIgreja(idIgreja));
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Salvar PG", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping( method = RequestMethod.POST)
	public ResponseEntity<Void> save(@Valid @RequestBody final PgNewDTO dto) {
		final PequenoGrupo obj = pgService.salvar(dto);
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Deletar Pg por id", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable final Integer id) {
		pgService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN','LIDER','PASTOR')")
	@Operation(summary = "Atualizar foto de perfil do PG", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/picture/{idPg}", method=RequestMethod.POST)
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") final MultipartFile file,
			@PathVariable Integer idPg) {
		final URI uri = pgService.uploadProfilePicture(file, idPg);
		return ResponseEntity.created(uri).build();
	}
	
}
