package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.OfertaServicoDTO;
import br.com.pic.folheto.dto.OfertaServicoNewDTO;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.OfertaServico;
import br.com.pic.folheto.respositories.IgrejaRepository;
import br.com.pic.folheto.respositories.OfertaServicoRepository;
import br.com.pic.folheto.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfertaServicoService {

	@Autowired
	private OfertaServicoRepository ofertaServicoDao;

	@Autowired
	private IgrejaRepository igrejaDao;

	public List<OfertaServicoDTO> buscarTodos() {
		final List<OfertaServico> resultado = ofertaServicoDao.findAllByOrderByDataCriacaoDesc();
		final List<OfertaServicoDTO> dto = new ArrayList<>();
		
		resultado.stream().forEach(oferta -> {
			OfertaServicoDTO ofertaDto = new OfertaServicoDTO();
			ofertaDto.setDataCriacao(DataUtil.converterLocalDateForStringWithFormatter(oferta.getDataCriacao(), "dd/MM/yyyy"));
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

	public List<OfertaServico> buscarPorIgreja(final Integer idIgreja) {
		return ofertaServicoDao.findByIgrejaId(idIgreja);
	}

	@Transactional
	public OfertaServico salvar(final OfertaServicoNewDTO dto) {
		final Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		final LocalDate dataCriacao = dto.getDataCriacao() != null ?
				DataUtil.converterStringLocalDate(dto.getDataCriacao(), "dd/MM/yyyy")
				: LocalDate.now();

		return ofertaServicoDao.save(OfertaServico.builder()
				.id(dto.getId())
				.nome(dto.getNome())
				.igreja(igreja)
				.descricao(dto.getDescricao())
				.emailServico(dto.getEmailServico())
				.instagram(dto.getInstagram())
				.endereco(dto.getEndereco())
				.dataCriacao(dataCriacao)
				.telefones(dto.getTelefones())
				.build());
	}

	public void deletar(final Integer id) {
		ofertaServicoDao.deleteById(id);
	}
}
