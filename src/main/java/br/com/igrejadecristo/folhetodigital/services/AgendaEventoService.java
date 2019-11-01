package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.time.LocalTime;
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
			existe = agendaEventoDao.existsByTitulo(dto.getTitulo());

			if (existe) {
				throw new RuntimeException("Ocorreu um erro, já existe Agenda/Evento com esse título no sistema!");
			}
		}

		Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		AgendaEvento agenda = new AgendaEvento(dto.getId(), dto.getTitulo(), igreja, dto.getDiaSemanaAtividade(),
				LocalTime.parse(dto.getHoraAtividade()), dto.getIsEvento(), dto.getDescricao(), LocalDate.parse(dto.getDataInicio()), LocalDate.parse(dto.getDataFim()) );

		return agendaEventoDao.save(agenda);
	}

	public void deletar(Integer id) {
		agendaEventoDao.deleteById(id);
	}
}
