package br.com.pic.folheto.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import br.com.pic.folheto.dto.MembroAlteraPerfilDTO;
import br.com.pic.folheto.entidades.Aniversariante;
import br.com.pic.folheto.entidades.EnderecoMembro;
import br.com.pic.folheto.respositories.EnderecoRepository;
import br.com.pic.folheto.respositories.MembroRepository;
import br.com.pic.folheto.services.exceptions.DataIntegrityException;
import br.com.pic.folheto.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.pic.folheto.dto.MembroAlteraDadosDTO;
import br.com.pic.folheto.dto.MembroDTO;
import br.com.pic.folheto.dto.MembroInfoDTO;
import br.com.pic.folheto.dto.MembroNewDTO;
import br.com.pic.folheto.entidades.Cidade;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.entidades.enums.Perfil;

@Service
public class MembroService {

	@Autowired
	private MembroRepository membroDao;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private AniversarianteService aniversarianteService;
	
	@Value("${img.prefix.membro.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;

	public Membro buscar(Integer id) {
//		UserSS user = UserService.authenticated();
//		if ( user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
//			throw new AuthorizationException("Acesso Negado");
//		}

		Optional<Membro> membro = membroDao.findById(id);
		return membro
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado! id: " + id + ", tipo:" + Membro.class.getName()));
	}

	@Transactional
	public Membro insert(Membro obj) {
		obj.setId(null);
		obj = membroDao.save(obj);
		salvarAniversariante(obj);
		
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	private void salvarAniversariante(Membro obj) {
		Aniversariante aniversariante = new Aniversariante();
		aniversariante.setDataNascimento(obj.getDataNascimento());
		aniversariante.setEmail(obj.getEmail());
		aniversariante.setNome(obj.getNome());
		aniversariante.setIgreja(obj.getIgreja());
		
		aniversarianteService.salvarAniversariante(aniversariante);
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

		return membroDao.findByIgrejaIdOrderByNome(idIgreja);
	}

	public MembroInfoDTO findByEmail(String email) {

		Membro obj = membroDao.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Tipo: " + Membro.class.getName());
		}

		String telefone = obj.getTelefones().size() > 0 ? obj.getTelefones().stream().findFirst().get() : null;

		List<Perfil> perfis = obj.getPerfis().stream().collect(Collectors.toList());

		MembroInfoDTO dto = new MembroInfoDTO(obj.getNome(), obj.getEmail(), obj.getCpf(), telefone,
				obj.getIgreja().getId(), perfis, obj.getId());

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
	
	public URI uploadProfilePicture(MultipartFile multipartFile, Integer idMembro) {
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + idMembro + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		
	}

}
