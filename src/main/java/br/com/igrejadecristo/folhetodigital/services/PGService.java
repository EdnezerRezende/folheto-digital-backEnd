package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.PgNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.PequenoGrupoRepository;

@Service
public class PGService {

	@Autowired
	private PequenoGrupoRepository pgDao;
	
	@Autowired
	private IgrejaRepository igrejaDao;
	
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
			existe = pgDao.existsByLiderAndResponsavelCasaAndDiaSemanaAtividadeAndHoraAtividade(dto.getLider(), dto.getResponsavelCasa(), dto.getDiaSemanaAtividade(), dto.getHoraAtividade());
			if (!existe) {
				throw new RuntimeException("Ocorreu um erro, PG já existe no sistema!");
			}
		}
		
		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();
		
		PequenoGrupo pg = new PequenoGrupo(dto.getId(), dto.getResponsavelCasa(),
				dto.getLider(), igreja,  dto.getDiaSemanaAtividade(), LocalTime.parse(dto.getHoraAtividade()));
		pg.setEndereco(dto.getEndereco());
		
		return pgDao.save(pg);
	}
	
	public void deletar(Integer id) {
		pgDao.deleteById(id);
	}
}
