package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.MissaoDTO;
import br.com.pic.folheto.dto.MissaoNewDTO;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.Missao;
import br.com.pic.folheto.respositories.IgrejaRepository;
import br.com.pic.folheto.respositories.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

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
	
	public MissaoDTO buscarPorMissao(final Integer idMissao) {
		final Missao missao = missaoDao.findById(idMissao).get();
		return MissaoDTO.builder()
				.autor(missao.getAutor())
				.id(missao.getId())
				.mensagem(missao.getMensagem())
				.titulo(missao.getTitulo())
				.build();
	}
	
	@Transactional
	public Missao salvarMissao(final MissaoNewDTO dto) {
		if (dto.getId() != null) {
			final Boolean existeMissao = missaoDao.existsById(dto.getId());
			if (!existeMissao) {
				throw new RuntimeException("Ocorreu um erro, mensagem não existe no sistema!");
			}
		}
		
		final Igreja igreja = igrejaDao.findById((dto.getIgrejaId())).get();

		return missaoDao.save(Missao.builder()
				.id(dto.getId())
				.mensagem(dto.getMensagem())
				.titulo(dto.getTitulo())
				.dataCriado(LocalDateTime.now())
				.autor(dto.getAutor())
				.igreja(igreja)
				.build());
	}
	
	@Transactional
	public void deletarMissao(final Integer idMissao) {
		missaoDao.deleteById(idMissao);
	}
	
	@Transactional
	public Missao buscarMissaoPorIdIgrejaEDataCriado(final Integer idIgreja, final LocalDateTime dataCriado,final LocalDateTime dataLimiteBusca) {
		return missaoDao.buscaMissaoPorIdIgrejaAndDataCriado(idIgreja, dataCriado, dataLimiteBusca);
	}
}
