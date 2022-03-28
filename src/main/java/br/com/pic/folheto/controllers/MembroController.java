package br.com.pic.folheto.controllers;

import br.com.pic.folheto.dto.*;
import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.services.MembroService;
import br.com.pic.folheto.util.DataUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/membros")
public class MembroController {

	@Autowired
	private MembroService membroService;
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar membro por id", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> buscaPorId(@PathVariable final Integer id){
		final Membro membro = membroService.buscar(id);
		
		return ResponseEntity.ok(membro);
	}

	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar membro por email", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/email", method=RequestMethod.GET)
	public ResponseEntity<MembroInfoDTO> find(@RequestParam(value="value") final String email) {
		final MembroInfoDTO obj = membroService.findByEmail(email);
		return ResponseEntity.ok().body(obj);
	}
	

	@RequestMapping(method=RequestMethod.POST)
	@Operation(summary = "Inserir membro", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> insert(@Valid @RequestBody final MembroNewDTO objDto) {
		Membro obj = membroService.fromDTO(objDto);
		obj = membroService.insert(obj);
		final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/perfil", method=RequestMethod.PUT)
	@Operation(summary = "Atualizar perfil", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> updatePerfil(@RequestBody final MembroAlteraPerfilDTO objDto) {
		membroService.update(objDto);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	@Operation(summary = "Atualizar Membro", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> updateMembro(@RequestBody final MembroAlteraDadosDTO objDto,@PathVariable final Integer id) {
		membroService.updateDados(objDto, id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Deletar Membro", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable final Integer id) {
		membroService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Buscar todos os membro por igreja", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/igreja/{idIgreja}", method=RequestMethod.GET)
	public ResponseEntity<List<MembroDTO>> findAll(@PathVariable final Integer idIgreja) {
		final List<Membro> list = membroService.findAll(idIgreja);
		final List<MembroDTO> listDto = list.stream().map(obj ->
				MembroDTO.builder()
						.id(obj.getId())
						.dataNascimento(DataUtil.converterLocalDateForStringWithFormatter(obj.getDataNascimento(), "dd/MM/yyyy"))
						.email(obj.getEmail())
						.nome(obj.getNome())
						.build()).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LIDER')")
	@Operation(summary = "Buscar membro por pageinação", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<MembroDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") final Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") final  Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome") final String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") final String direction) {
		final Page<Membro> list = membroService.findPage(page, linesPerPage, orderBy, direction);
		final Page<MembroDTO> listDto = list.map(obj -> MembroDTO.builder()
												.id(obj.getId())
												.dataNascimento(DataUtil.converterLocalDateForStringWithFormatter(obj.getDataNascimento(), "dd/MM/yyyy"))
												.email(obj.getEmail())
												.nome(obj.getNome())
												.build());
		return ResponseEntity.ok().body(listDto);
	}	
	
	@PreAuthorize("hasAnyRole('MEMBRO','VISITANTE')")
	@Operation(summary = "Buscar todos os aniversariantes", security = @SecurityRequirement(name = "bearerAuth"))
	@RequestMapping(value="/aniversariantes/{idIgreja}", method=RequestMethod.GET)
	public ResponseEntity<List<MembroDTO>> buscarTodosAniversariantes(@PathVariable final Integer idIgreja) {
		final List<Membro> list = membroService.findAllMembrosByDataNascimento(idIgreja);
		final List<MembroDTO> listDto = list.stream().map(obj -> MembroDTO.builder()
				.id(obj.getId())
				.dataNascimento(DataUtil.converterLocalDateForStringWithFormatter(obj.getDataNascimento(), "dd/MM/yyyy"))
				.email(obj.getEmail())
				.nome(obj.getNome())
				.build()).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value="/picture/{idMembro}", method=RequestMethod.POST)
	@Operation(summary = "Atualizar foto perfil", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name="file") final MultipartFile file,
			@PathVariable final Integer idMembro) {
		final URI uri = membroService.uploadProfilePicture(file, idMembro);
		return ResponseEntity.created(uri).build();
	}
}
