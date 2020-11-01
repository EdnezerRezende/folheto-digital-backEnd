package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.AgendaEventoNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.AgendaEvento;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.respositories.AgendaEventoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;

@Service
public class AgendaEventoService {

	@Autowired
	private AgendaEventoRepository agendaEventoDao;

	@Autowired
	private IgrejaRepository igrejaDao;

	public List<AgendaEvento> buscarTodos() {
		return agendaEventoDao.findAllByOrderByDiaSemanaAtividade();
	}

	public List<AgendaEvento> buscarPorIgreja(Integer idIgreja) {
		return agendaEventoDao.findByIgrejaId(idIgreja);
	}

	@Transactional
	public AgendaEvento salvar(AgendaEventoNewDTO dto) {
		Boolean existe = false;

		if (dto.getId() == null) {
			existe = agendaEventoDao.existsByTituloAndDiaSemanaAtividade(dto.getTitulo(), dto.getDiaSemanaAtividade());

			if (existe) {
				throw new RuntimeException("Ocorreu um erro, já existe Agenda/Evento com esse título e dia da semana no sistema!");
			}
		}

		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(!dto.getIsEvento()) {
			dto.setDataInicio(LocalDate.now().toString());
			dto.setDataFim(LocalDate.now().plusYears(30l).toString());
		}
		
		AgendaEvento agenda = new AgendaEvento(dto.getId(), dto.getTitulo(), igreja, dto.getDiaSemanaAtividade(),
				LocalTime.parse(dto.getHoraAtividade()), dto.getIsEvento(), dto.getDescricao(), LocalDate.parse(dto.getDataInicio(), formatter), LocalDate.parse(dto.getDataFim(), formatter), dto.getLink());

		return agendaEventoDao.save(agenda);
	}

	public void deletar(Integer id) {
		agendaEventoDao.deleteById(id);
	}
}
