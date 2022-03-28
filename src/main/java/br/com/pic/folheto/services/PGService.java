package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.PgNewDTO;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.PequenoGrupo;
import br.com.pic.folheto.respositories.EnderecoPGRepository;
import br.com.pic.folheto.respositories.IgrejaRepository;
import br.com.pic.folheto.respositories.PequenoGrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.time.LocalTime;
import java.util.List;

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
	
	public List<PequenoGrupo> buscarPorIgreja(final Integer idIgreja) {
		return pgDao.findByIgrejaId(idIgreja);
	}
	
	
	@Transactional
	public PequenoGrupo salvar(final PgNewDTO dto) {
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
		
		final Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		final PequenoGrupo pg = PequenoGrupo.builder()
				.id(dto.getId())
				.responsavelCasa(dto.getResponsavelCasa())
				.lider(dto.getLider())
				.igreja(igreja)
				.diaSemanaAtividade(dto.getDiaSemanaAtividade())
				.horaAtividade(LocalTime.parse(dto.getHoraAtividade()))
				.endereco(dto.getEndereco())
				.build();

		pgDao.save(pg);
		
		enderecoPGRepository.save(dto.getEndereco());

		return pgDao.save(pg);
	}
	
	public void deletar(final Integer id) {
		pgDao.deleteById(id);
	}
	
	public URI uploadProfilePicture(final MultipartFile multipartFile, final Integer idPg) {
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		final String fileName = prefix + idPg + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}
}
