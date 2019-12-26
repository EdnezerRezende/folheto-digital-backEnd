package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.MembroAlteraDadosDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroAlteraPerfilDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroInfoDTO;
import br.com.igrejadecristo.folhetodigital.dto.MembroNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Cidade;
import br.com.igrejadecristo.folhetodigital.entidades.EnderecoMembro;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.entidades.enums.Perfil;
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
		return membro
				.orElseThrow(() -> new br.com.igrejadecristo.folhetodigital.services.exceptions.ObjectNotFoundException(
						"Objeto não encontrado! id: " + id + ", tipo:" + Membro.class.getName()));
	}

	@Transactional
	public Membro insert(Membro obj) {
		obj.setId(null);
		obj = membroDao.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Membro update(MembroAlteraPerfilDTO obj) {
		Membro newObj = buscar(obj.getId());
		updateData(newObj, obj);
		if (!newObj.getPerfis().contains(Perfil.MEMBRO)) {
			newObj.addPerfil(Perfil.MEMBRO);
		}
		return membroDao.save(newObj);
	}

	@Transactional
	public Membro updateDados(MembroAlteraDadosDTO obj, Integer id) {
		Membro newObj = buscar(id);
		
		List<EnderecoMembro> enderecos = enderecoRepository.findAllByMembroId(id);
		List<EnderecoMembro> enderecosTemp = new ArrayList<>();
		enderecos.forEach(endereco -> {
			if(endereco.getId() == obj.getEnderecos().get(0).getId()) {
				endereco = obj.getEnderecos().get(0);
			}
			enderecosTemp.add(endereco);
		});
		enderecosTemp.get(0).setMembro(newObj);

		updateDados(newObj, obj);
		
		newObj = membroDao.save(newObj);
		
		enderecoRepository.save(enderecosTemp.get(0));
		
		return newObj;
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			membroDao.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há alguma coisa associada");
		}
	}

	public List<Membro> findAll(Integer idIgreja) {

		return membroDao.findByIgrejaId(idIgreja);
	}

	public MembroInfoDTO findByEmail(String email) {

		Membro obj = membroDao.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Tipo: " + Membro.class.getName());
		}

		String telefone = obj.getTelefones().size() > 0 ? obj.getTelefones().stream().findFirst().get() : null;

		List<Perfil> perfis = obj.getPerfis().stream().collect(Collectors.toList());

		MembroInfoDTO dto = new MembroInfoDTO(obj.getNome(), obj.getEmail(), obj.getCpf(), telefone,
				obj.getIgreja().getId(), perfis);

		return dto;
	}

	public Page<Membro> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return membroDao.findAll(pageRequest);
	}

	public Membro fromDTO(MembroDTO objDto) {
		return new Membro(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null, null);
	}

	public Membro fromDTO(MembroNewDTO objDto) {
		Igreja igreja = new Igreja(objDto.getIgrejaId(), null, null, null);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dataNascimento = LocalDate.parse(objDto.getDataNascimento(), formatter);

		Membro cli = new Membro(null, objDto.getNome(), objDto.getEmail(), objDto.getCpf(),
				pe.encode(objDto.getSenha()), igreja, dataNascimento);
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		EnderecoMembro end = new EnderecoMembro(null, objDto.getLogradouro(), objDto.getNumero(),
				objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone());
		if (objDto.getCelular() != null) {
			cli.getTelefones().add(objDto.getCelular());
		}
		return cli;
	}

	private void updateData(Membro newObj, MembroAlteraPerfilDTO obj) {
		newObj.setPerfis(obj.getPerfis());
	}

	private void updateDados(Membro newObj, MembroAlteraDadosDTO obj) {
		newObj.setEmail(obj.getEmail());
		newObj.setTelefones(obj.getTelefones());
	}

	public List<Membro> findAllMembrosByDataNascimento(Integer idIgreja) {
		LocalDate dataInicial = LocalDate.now().minusWeeks(1);
		LocalDate dataFinal = LocalDate.now();
		List<Membro> membros = membroDao.buscaMembrosPorDataInicioEFim(dataInicial, dataFinal);
		return membros;
	}

}
