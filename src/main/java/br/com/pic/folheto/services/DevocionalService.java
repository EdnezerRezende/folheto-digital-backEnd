package br.com.pic.folheto.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import br.com.pic.folheto.respositories.DevocionalRepository;
import br.com.pic.folheto.respositories.IgrejaRepository;
import br.com.pic.folheto.respositories.ReferenciaRepository;
import br.com.pic.folheto.respositories.VersiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pic.folheto.dto.DevocionalNewDTO;
import br.com.pic.folheto.entidades.Devocional;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.Referencia;
import br.com.pic.folheto.entidades.Versiculo;
import br.com.pic.folheto.respositories.DevocionalComentarioRepository;
import br.com.pic.folheto.util.DataUtil;

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

	public List<Devocional> buscarPorIgreja(Integer idIgreja, Integer idMembro ) {
		LocalDate dataInicio = DataUtil.obterDataInicialBoletimSemana();
			
		LocalDate dataFim = dataInicio.plusDays(6);

		List<Devocional> devocionais = buscarPorIgrejaEDataCriacao(idIgreja, dataInicio, dataFim);
		
		verificarDevocionalLido(idMembro, devocionais);
		
		return devocionais;
	}
	
	public List<Devocional> buscarDevocionaisAntigosPorIgreja(Integer idIgreja, Integer idMembro ) {
		LocalDate dataInicio = DataUtil.obterDataInicialBoletimSemana();
			
		List<Devocional> devocionais = devocionalDao.buscaDevocionalAntigosPorIdIgrejaAndDataCriado(idIgreja, Boolean.FALSE, dataInicio);

		verificarDevocionalLido(idMembro, devocionais);
		
		return devocionais;
	}

	private void verificarDevocionalLido(Integer idMembro, List<Devocional> devocionais) {
		devocionais.stream().forEach(devocional ->{ 
				devocional.setIsLido(devocionalComentarioDao.existsByMembroIdAndReferenciaIdAndIsDeletado(idMembro, devocional.getReferencia().getId(), Boolean.FALSE));
			}
		);
	}

	public List<Devocional> buscarPorIgrejaEDataCriacao(Integer idIgreja, LocalDate dataCriado, LocalDate dataLimiteBusca) {
		return devocionalDao.buscaDevocionalPorIdIgrejaAndDataCriado(idIgreja, Boolean.FALSE, dataCriado, dataLimiteBusca);
	}
	
	@Transactional
	public Devocional salvar(DevocionalNewDTO dto) {
		Referencia referencia = new Referencia(dto.getReferencia());
		referencia = referenciaRepository.save(referencia);

		for(Versiculo versiculo : referencia.getVerses()) {
			versiculo.setReferencia(referencia);
		};
		
		versiculoRepository.saveAll(referencia.getVerses());
		
		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Devocional devocional = new Devocional(dto.getId(), referencia, igreja, dto.getDescricao() ,
				dto.getDataCriacao() != null ? LocalDate.parse(dto.getDataCriacao(), formatter):LocalDate.now(), dto.getTextoReferencia());

		return devocionalDao.save(devocional);
	}

	public void deletar(Integer id) {
		Devocional devocional = devocionalDao.findById(id).get();
		devocional.setIsDeletado(Boolean.TRUE);
		devocionalDao.saveAndFlush(devocional);
	}
}
