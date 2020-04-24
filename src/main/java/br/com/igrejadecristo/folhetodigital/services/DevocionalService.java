package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.DevocionalNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Devocional;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.respositories.DevocionalRepository;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.util.DataUtil;

@Service
public class DevocionalService {

	@Autowired
	private DevocionalRepository devocionalDao;

	@Autowired
	private IgrejaRepository igrejaDao;

	public List<Devocional> buscarTodos() {
		return devocionalDao.findAllByOrderByDataCriacaoDesc();
	}

	public List<Devocional> buscarPorIgreja(Integer idIgreja) {
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy HH:mm").withLocale(new Locale("pt", "br"));
		LocalDateTime dataHoje = LocalDateTime.now();

		String dataBoletimGerado = DataUtil.obterDataGeracaoBoletim(dataHoje.toLocalDate());
		LocalDateTime dataInicio = LocalDateTime.parse(dataBoletimGerado + " 00:00",parser);
			
		LocalDateTime dataFim = LocalDateTime.parse(dataBoletimGerado + " 11:59",parser).plusDays(6);
		
		List<Devocional> devocionais = devocionalDao.findByIgrejaId(idIgreja);
		devocionais.stream().forEach(devocional ->{ 
				if(!devocional.getDataCriacao().isBefore(dataInicio.toLocalDate()) && 
						!devocional.getDataCriacao().isAfter(dataFim.toLocalDate())) {
					devocional.setIsAtual(true);
				}
			}
		);
		
		
		return devocionais;
	}

	public List<Devocional> buscarPorIgrejaEDataCriacao(Integer idIgreja, LocalDate dataCriado, LocalDate dataLimiteBusca) {
		return devocionalDao.buscaDevocionalPorIdIgrejaAndDataCriado(idIgreja, dataCriado, dataLimiteBusca);
	}

	
	@Transactional
	public Devocional salvar(DevocionalNewDTO dto) {
		Boolean existe = false;

		if (dto.getId() == null) {
			existe = devocionalDao.existsByReferencia(dto.getReferencia());

			if (existe) {
				throw new RuntimeException("Ocorreu um erro, já existe o Devocional com essa referencia no sistema!");
			}
		}

		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		Devocional agenda = new Devocional(dto.getId(), dto.getReferencia(), igreja, dto.getDescricao() ,
				dto.getDataCriacao() != null ? LocalDate.parse(dto.getDataCriacao(), formatter):LocalDate.now());

		return devocionalDao.save(agenda);
	}

	public void deletar(Integer id) {
		devocionalDao.deleteById(id);
	}
}
