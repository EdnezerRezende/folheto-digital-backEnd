package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.OfertaServicoNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.OfertaServico;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.OfertaServicoRepository;

@Service
public class OfertaServicoService {

	@Autowired
	private OfertaServicoRepository ofertaServicoDao;

	@Autowired
	private IgrejaRepository igrejaDao;

	public List<OfertaServico> buscarTodos() {
		return ofertaServicoDao.findAllByOrderByDataCriacaoDesc();
	}

	public List<OfertaServico> buscarPorIgreja(Integer idIgreja) {
		return ofertaServicoDao.findByIgrejaId(idIgreja);
	}

	@Transactional
	public OfertaServico salvar(OfertaServicoNewDTO dto) {
		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		OfertaServico servico = new OfertaServico(dto.getId(), dto.getNome() ,igreja, dto.getDescricao() ,
				dto.getEmailServico(), dto.getInstagram(), dto.getFacebook(), dto.getEndereco(),
				dto.getDataCriacao() != null ? LocalDate.parse(dto.getDataCriacao()):LocalDate.now());

		servico.getTelefones().addAll(dto.getTelefones());
		return ofertaServicoDao.save(servico);
	}

	public void deletar(Integer id) {
		ofertaServicoDao.deleteById(id);
	}
}
