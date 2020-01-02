package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.OfertaServicoDTO;
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

	public List<OfertaServicoDTO> buscarTodos() {
		List<OfertaServico> resultado = ofertaServicoDao.findAllByOrderByDataCriacaoDesc();
		List<OfertaServicoDTO> dto = new ArrayList<>();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		resultado.stream().forEach(oferta -> {
			OfertaServicoDTO ofertaDto = new OfertaServicoDTO();
			ofertaDto.setDataCriacao(oferta.getDataCriacao().format(formatter));
			ofertaDto.setDescricao(oferta.getDescricao());
			ofertaDto.setEmailServico(oferta.getEmailServico());
			ofertaDto.setEndereco(oferta.getEndereco());
			ofertaDto.setFacebook(oferta.getFacebook());
			ofertaDto.setId(oferta.getId());
			ofertaDto.setIdIgreja(oferta.getIgreja().getId().toString());
			ofertaDto.setInstagram(oferta.getInstagram());
			ofertaDto.setNome(oferta.getNome());
			ofertaDto.setTelefones(oferta.getTelefones());
			dto.add(ofertaDto);
		});
		return dto;
	}

	public List<OfertaServico> buscarPorIgreja(Integer idIgreja) {
		return ofertaServicoDao.findByIgrejaId(idIgreja);
	}

	@Transactional
	public OfertaServico salvar(OfertaServicoNewDTO dto) {
		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		OfertaServico servico = new OfertaServico(dto.getId(), dto.getNome() ,igreja, dto.getDescricao() ,
				dto.getEmailServico(), dto.getInstagram(), dto.getFacebook(), dto.getEndereco(),
				dto.getDataCriacao() != null ? LocalDate.parse(dto.getDataCriacao(),formatter):LocalDate.now());

		servico.getTelefones().addAll(dto.getTelefones());
		return ofertaServicoDao.save(servico);
	}

	public void deletar(Integer id) {
		ofertaServicoDao.deleteById(id);
	}
}
