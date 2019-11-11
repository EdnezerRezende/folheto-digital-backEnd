package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.MembroDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Cidade;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoMembro;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.respositories.EnderecoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MembroRepository;
import br.com.igrejadecristo.folhetodigital.services.exceptions.DataIntegrityException;
import br.com.igrejadecristo.folhetodigital.services.exceptions.ObjectNotFoundException;

@Service
public class MembroService {

	@Autowired
	private MembroRepository membroDao;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	public Membro buscar(Integer id) {
//		UserSS user = UserService.authenticated();
//		if ( user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
//			throw new AuthorizationException("Acesso Negado");
//		}
		
		Optional<Membro> membro = membroDao.findById(id);
		return membro.orElseThrow(()-> 
			new br.com.igrejadecristo.folhetodigital.services.exceptions.ObjectNotFoundException(
					"Objeto não encontrado! id: " + id + ", tipo:"+Membro.class.getName()));
	}
	
	@Transactional
	public Membro insert(Membro obj) {
		obj.setId(null);
		obj = membroDao.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	public Membro update(Membro obj) {
		Membro newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return membroDao.save(newObj);
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			membroDao.deleteById(id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há alguma coisa associada");
		}
	}
	
	public List<Membro> findAll(Integer idIgreja) {
		
		return membroDao.findByIgrejaId(idIgreja);
	}
	
	public Membro findByEmail(String email) {
//		UserSS user = UserService.authenticated();
//		if ( user==null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
//			throw new AuthorizationException("Acesso Negado");
//		}
		
		Membro obj = membroDao.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Tipo: " + Membro.class.getName());
		}
		return obj;
	}
	
	public Page<Membro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return membroDao.findAll(pageRequest);
	}
	
	public Membro fromDTO(MembroDTO objDto) {
		return new Membro(objDto.getId(), objDto.getNome(), objDto.getEmail(),null, null, null);
	}
	
	public Membro fromDTO(MembroNewDTO objDto) {
		Igreja igreja = new Igreja(objDto.getIgrejaId(),null, null );
		Membro cli = new Membro(null, objDto.getNome(), objDto.getEmail(),objDto.getCpf(), pe.encode(objDto.getSenha()), igreja);
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		EnderecoMembro end = new EnderecoMembro(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	private void updateData(Membro newObj, Membro obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	
	
	
}
