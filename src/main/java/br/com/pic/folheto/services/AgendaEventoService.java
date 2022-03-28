package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.AgendaEventoNewDTO;
import br.com.pic.folheto.entidades.AgendaEvento;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.respositories.AgendaEventoRepository;
import br.com.pic.folheto.respositories.IgrejaRepository;
import br.com.pic.folheto.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class AgendaEventoService {

	public static final long YEARS_TO_ADD = 30L;
	@Autowired
	private AgendaEventoRepository agendaEventoDao;

	@Autowired
	private IgrejaRepository igrejaDao;

	public List<AgendaEvento> buscarTodos() {
		return agendaEventoDao.findAllByOrderByDiaSemanaAtividade();
	}

	public List<AgendaEvento> buscarPorIgreja(final Integer idIgreja) {
		return agendaEventoDao.findByIgrejaId(idIgreja);
	}

	@Transactional
	public AgendaEvento salvar(final AgendaEventoNewDTO dto) {
		Boolean existe;

		if (dto.getId() == null) {
			existe = agendaEventoDao.existsByTituloAndDiaSemanaAtividade(dto.getTitulo(), dto.getDiaSemanaAtividade());

			if (existe) {
				throw new RuntimeException("Ocorreu um erro, já existe Agenda/Evento com esse título e dia da semana no sistema!");
			}
		}

		final Igreja igreja = igrejaDao.findById(Integer.parseInt(dto.getIdIgreja())).get();

		if(!dto.getIsEvento()) {
			dto.setDataInicio(LocalDate.now().toString());
			dto.setDataFim(LocalDate.now().plusYears(YEARS_TO_ADD).toString());
		}
		
		return agendaEventoDao.save(AgendaEvento.builder()
				.id(dto.getId())
				.titulo(dto.getTitulo())
				.igreja(igreja)
				.diaSemanaAtividade(dto.getDiaSemanaAtividade())
				.horaAtividade(DataUtil.converterStringParaLocalTimeFormatado(dto.getHoraAtividade()))
				.isEvento(dto.getIsEvento())
				.descricao(dto.getDescricao())
				.dataInicio(DataUtil.converterStringLocalDate(dto.getDataInicio(), "yyyy-MM-dd"))
				.dataFim(DataUtil.converterStringLocalDate(dto.getDataFim(), "yyyy-MM-dd"))
				.link(dto.getLink())
				.build());
	}

	public void deletar(final Integer id) {
		agendaEventoDao.deleteById(id);
	}
}
