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
import br.com.pic.folheto.util.DataUtil;
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

	public Membro buscar(final Integer id) {
		final Optional<Membro> membro = membroDao.findById(id);
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

	private void salvarAniversariante(final Membro obj) {
		final Aniversariante aniversariante = new Aniversariante();
		aniversariante.setDataNascimento(obj.getDataNascimento());
		aniversariante.setEmail(obj.getEmail());
		aniversariante.setNome(obj.getNome());
		aniversariante.setIgreja(obj.getIgreja());
		
		aniversarianteService.salvarAniversariante(aniversariante);
	}

	public Membro update(final MembroAlteraPerfilDTO obj) {
		final Membro newObj = buscar(obj.getId());
		updateData(newObj, obj);
		if (!newObj.getPerfis().contains(Perfil.MEMBRO)) {
			newObj.addPerfil(Perfil.MEMBRO);
		}
		return membroDao.save(newObj);
	}

	@Transactional
	public Membro updateDados(final MembroAlteraDadosDTO obj,final Integer id) {
		Membro newObj = buscar(id);
		
		final List<EnderecoMembro> enderecos = enderecoRepository.findAllByMembroId(id);
		final List<EnderecoMembro> enderecosTemp = new ArrayList<>();
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

	public void delete(final Integer id) {
		try {
			membroDao.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há alguma coisa associada");
		}
	}

	public List<Membro> findAll(final Integer idIgreja) {

		return membroDao.findByIgrejaIdOrderByNome(idIgreja);
	}

	public MembroInfoDTO findByEmail(final String email) {

		final Membro obj = membroDao.findByEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Tipo: " + Membro.class.getName());
		}

		final String telefone = obj.getTelefones().size() > 0 ? obj.getTelefones().stream().findFirst().get() : null;

		final List<Perfil> perfis = obj.getPerfis().stream().collect(Collectors.toList());

		return MembroInfoDTO.builder()
				.nome(obj.getNome())
				.email(obj.getEmail())
				.cpf(obj.getCpf())
				.telefone(telefone)
				.igrejaId(obj.getIgreja().getId())
				.perfis(perfis)
				.id(obj.getId())
				.build();
	}

	public Page<Membro> findPage(final Integer page,final Integer linesPerPage, final String orderBy,final String direction) {
		final PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return membroDao.findAll(pageRequest);
	}

	public Membro fromDTO(final MembroNewDTO objDto) {
		Igreja igreja = Igreja.builder()
				.id(objDto.getIgrejaId())
				.build();

		final LocalDate dataNascimento = DataUtil.converterStringLocalDate(objDto.getDataNascimento(), "yyyy-MM-dd");

		final Membro membro = Membro.builder()
				.nome(objDto.getNome())
				.email(objDto.getEmail())
				.cpf(objDto.getCpf())
				.senha(pe.encode(objDto.getSenha()))
				.igreja(igreja)
				.dataNascimento(dataNascimento)
				.build();

		final Cidade cid = Cidade.builder()
				.id(objDto.getCidadeId())
				.build();
		final EnderecoMembro end = EnderecoMembro.builder()
				.logradouro(objDto.getLogradouro())
				.numero(objDto.getNumero())
				.complemento(objDto.getComplemento())
				.bairro(objDto.getBairro())
				.cep(objDto.getCep())
				.membro(membro)
				.cidade(cid)
				.build();

		membro.getEnderecos().add(end);
		membro.getTelefones().add(objDto.getTelefone());

		if (objDto.getCelular() != null) {
			membro.getTelefones().add(objDto.getCelular());
		}
		return membro;
	}

	private void updateData(final Membro newObj, final MembroAlteraPerfilDTO obj) {
		newObj.setPerfis(obj.getPerfis());
	}

	private void updateDados(final Membro newObj,final MembroAlteraDadosDTO obj) {
		newObj.setEmail(obj.getEmail());
		newObj.setTelefones(obj.getTelefones());
	}

	public List<Membro> findAllMembrosByDataNascimento(final Integer idIgreja) {
		final LocalDate dataInicial = LocalDate.now().minusWeeks(1);
		final LocalDate dataFinal = LocalDate.now();
		return membroDao.buscaMembrosPorDataInicioEFim(dataInicial, dataFinal);
	}
	
	public URI uploadProfilePicture(final MultipartFile multipartFile,final Integer idMembro) {
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		final String fileName = prefix + idMembro + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		
	}

}
