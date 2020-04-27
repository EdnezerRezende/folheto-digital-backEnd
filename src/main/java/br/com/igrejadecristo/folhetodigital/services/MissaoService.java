package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.MissaoDTO;
import br.com.igrejadecristo.folhetodigital.dto.MissaoNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Missao;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MissaoRepository;

@Service
public class MissaoService {

	@Autowired
	private MissaoRepository missaoDao;

	@Autowired
	private IgrejaRepository igrejaDao;
	
	public List<Missao> buscarTodos() {
		List<Missao> missoes = missaoDao.findAllByOrderByDataCriadoDesc();
		return missoes;
	}
	
	public MissaoDTO buscarPorMissao(Integer idMissao) {
		Missao missao = missaoDao.findById(idMissao).get();
		return new MissaoDTO(missao);
	}
	
	@Transactional
	public Missao salvarMissao(MissaoNewDTO dto) {
		if (dto.getId() != null) {
			Boolean existeMissao = missaoDao.existsById(dto.getId());
			if (!existeMissao) {
				throw new RuntimeException("Ocorreu um erro, mensagem não existe no sistema!");
			}
		}
		
		Igreja igreja = igrejaDao.findById((dto.getIgrejaId())).get();
		
		Missao missao = new Missao(dto.getId(),
				dto.getMensagem(), 
				dto.getAutor(), LocalDateTime.now(),  dto.getTitulo(), igreja);
		return missaoDao.save(missao);
	}
	
	@Transactional
	public void deletarMissao(Integer idMissao) {
		
		missaoDao.deleteById(idMissao);
	}
	
	@Transactional
	public Missao buscarMissaoPorIdIgrejaEDataCriado(Integer idIgreja, LocalDateTime dataCriado, LocalDateTime dataLimiteBusca) {
		
		return missaoDao.buscaMissaoPorIdIgrejaAndDataCriado(idIgreja, dataCriado, dataLimiteBusca); 
	}
}
