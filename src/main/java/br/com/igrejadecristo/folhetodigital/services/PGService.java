package br.com.igrejadecristo.folhetodigital.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.igrejadecristo.folhetodigital.dto.PgNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;
import br.com.igrejadecristo.folhetodigital.respositories.EnderecoPGRepository;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.PequenoGrupoRepository;

@Service
public class PGService {

	@Autowired
	private PequenoGrupoRepository pgDao;
	
	@Autowired
	private IgrejaRepository igrejaDao;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private S3Service s3Service;
	
	@Value("${img.prefix.pg.profile}")
	private String prefix;
	
	@Value("${img.profile.size}")
	private Integer size;
	
	@Autowired
	private EnderecoPGRepository enderecoPGRepository;
	
	public List<PequenoGrupo> buscarTodos() {
		return pgDao.findAllByOrderByDiaSemanaAtividade();
	}
	
	public List<PequenoGrupo> buscarPorIgreja(Integer idIgreja) {
		return pgDao.findByIgrejaId(idIgreja);
	}
	
	
	@Transactional
	public PequenoGrupo salvar(PgNewDTO dto) {
		Boolean existe = false;
		
		if (dto.getId() != null) {
			existe = pgDao.existsById(dto.getId());
			if (!existe) {
				throw new RuntimeException("Ocorreu um erro, PG não existe no sistema!");
			}
		}else {
			existe = pgDao.existsByLiderAndResponsavelCasaAndDiaSemanaAtividade(dto.getLider(), dto.getResponsavelCasa(), dto.getDiaSemanaAtividade());
			if (existe) {
				throw new RuntimeException("Ocorreu um erro, PG já existe no sistema!");
			}
		}
		
		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();
		
		PequenoGrupo pg = new PequenoGrupo(dto.getId(), dto.getResponsavelCasa(),
				dto.getLider(), igreja,  dto.getDiaSemanaAtividade(), LocalTime.parse(dto.getHoraAtividade()));
		pgDao.save(pg);
		
		
		enderecoPGRepository.save(dto.getEndereco());
		pg.setEndereco(dto.getEndereco());
		
		return pgDao.save(pg);
	}
	
	public void deletar(Integer id) {
		pgDao.deleteById(id);
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile, Integer idPg) {
//		UserSS user = UserService.authenticated();
//		if (user == null) {
//			throw new AuthorizationException("Acesso negado");
//		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + idPg + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
