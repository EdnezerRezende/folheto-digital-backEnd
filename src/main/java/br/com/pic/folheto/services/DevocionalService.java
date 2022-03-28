package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.DevocionalNewDTO;
import br.com.pic.folheto.entidades.Devocional;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.Referencia;
import br.com.pic.folheto.entidades.Versiculo;
import br.com.pic.folheto.respositories.*;
import br.com.pic.folheto.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DevocionalService {

	@Autowired
	private DevocionalRepository devocionalDao;

	@Autowired
	private IgrejaRepository igrejaDao;
	
	@Autowired
	private ReferenciaRepository referenciaRepository;
	
	@Autowired
	private VersiculoRepository versiculoRepository;


	@Autowired
	private DevocionalComentarioRepository devocionalComentarioDao;
	
	public List<Devocional> buscarTodos() {
		return devocionalDao.findAllByIsDeletadoOrderByDataCriacaoDesc(Boolean.FALSE);
	}

	public List<Devocional> buscarPorIgreja(final Integer idIgreja,final  Integer idMembro ) {
		final LocalDate dataInicio = DataUtil.obterDataInicialBoletimSemana();
			
		final LocalDate dataFim = dataInicio.plusDays(6);

		final List<Devocional> devocionais = buscarPorIgrejaEDataCriacao(idIgreja, dataInicio, dataFim);
		
		verificarDevocionalLido(idMembro, devocionais);
		
		return devocionais;
	}
	
	public List<Devocional> buscarDevocionaisAntigosPorIgreja(final Integer idIgreja,final Integer idMembro ) {
		final LocalDate dataInicio = DataUtil.obterDataInicialBoletimSemana();
			
		final List<Devocional> devocionais = devocionalDao.buscaDevocionalAntigosPorIdIgrejaAndDataCriado(idIgreja, Boolean.FALSE, dataInicio);

		verificarDevocionalLido(idMembro, devocionais);
		
		return devocionais;
	}

	private void verificarDevocionalLido(final Integer idMembro,final List<Devocional> devocionais) {
		devocionais.stream().forEach(devocional ->{ 
				devocional.setIsLido(devocionalComentarioDao.existsByMembroIdAndReferenciaIdAndIsDeletado(idMembro, devocional.getReferencia().getId(), Boolean.FALSE));
			}
		);
	}

	public List<Devocional> buscarPorIgrejaEDataCriacao(final Integer idIgreja,final LocalDate dataCriado,final LocalDate dataLimiteBusca) {
		return devocionalDao.buscaDevocionalPorIdIgrejaAndDataCriado(idIgreja, Boolean.FALSE, dataCriado, dataLimiteBusca);
	}
	
	@Transactional
	public Devocional salvar(final DevocionalNewDTO dto) {
		final Referencia referencia = referenciaRepository.save(new Referencia(dto.getReferencia()));

		for(Versiculo versiculo : referencia.getVerses()) {
			versiculo.setReferencia(referencia);
		};
		
		versiculoRepository.saveAll(referencia.getVerses());
		
		final Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		final Devocional devocional = Devocional.builder()
				.id(dto.getId())
				.referencia(referencia)
				.igreja(igreja)
				.descricao(dto.getDescricao())
				.dataCriacao(dto.getDataCriacao() != null ? LocalDate.parse(dto.getDataCriacao(), formatter):LocalDate.now())
				.build();

		return devocionalDao.save(devocional);
	}

	public void deletar(final Integer id) {
		devocionalDao.deleteById(id);
	}
}
