package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.AniversarianteAlteraDTO;
import br.com.pic.folheto.dto.AniversarianteNewDTO;
import br.com.pic.folheto.entidades.Aniversariante;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.respositories.AniversarianteRepository;
import br.com.pic.folheto.services.exceptions.DataIntegrityException;
import br.com.pic.folheto.services.exceptions.ObjectNotFoundException;
import br.com.pic.folheto.util.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AniversarianteService {

	@Autowired
	private AniversarianteRepository aniversarianteRepository;
	
	@Transactional
	public Aniversariante buscar(final Integer id) {
		final Optional<Aniversariante> aniversariante = aniversarianteRepository.findById(id);
		return aniversariante
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado! id: " + id + ", tipo:" + Aniversariante.class.getName()));
	}
	
	@Transactional
	public Aniversariante salvarAniversariante(final Aniversariante obj) {
		return aniversarianteRepository.save(Aniversariante.builder()
				.dataNascimento(obj.getDataNascimento())
				.email(obj.getEmail())
				.nome(obj.getNome())
				.igreja(obj.getIgreja())
				.build());
	}
	
	@Transactional
	public List<Aniversariante> buscarAniversariantesPorIdIgreja(final Integer idIgreja){
		final LocalDateTime dataHoje = LocalDateTime.now();

		final LocalDate dataBoletimGerado = obterDataGeracaoBoletim(dataHoje.toLocalDate());
			
		final LocalDate dataLimiteBusca = dataBoletimGerado.plusDays(6);
		
		return buscarAniversariantesPorIgrejaEDataNascimento(idIgreja, 
				dataBoletimGerado, dataLimiteBusca);
	}
	
	public LocalDate obterDataGeracaoBoletim(final LocalDate dataDomingo) {
		final DayOfWeek dayOfWeek = dataDomingo.getDayOfWeek();
		
		return DataUtil.validarEObterDataDomingo(dataDomingo, dayOfWeek);
	}

	@Transactional
	public List<Aniversariante> buscarAniversariantesPorIgrejaEDataNascimento(final Integer idIgreja, final LocalDate dataInicio, final LocalDate dataFim){
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd");
		return aniversarianteRepository.buscaAniversariantesPorIdIgrejaAndDataCriado(idIgreja, dataInicio.format(formatter), dataFim.format(formatter));
	}

	public void delete(final Integer id) {
		buscar(id);
		try {
			aniversarianteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há algo associada");
		}
	}
	
	public Aniversariante fromDTO(final AniversarianteNewDTO objDto) {

		return Aniversariante.builder()
				.nome(objDto.getNome())
				.dataNascimento(DataUtil.converterStringLocalDate(objDto.getDataNascimento(), "yyyy-MM-dd"))
				.email(objDto.getEmail())
				.igreja(Igreja.builder().id(objDto.getId()).build())
				.build();
	}
	
	@Transactional
	public Aniversariante updateDados(final AniversarianteAlteraDTO obj, final Integer id) {
		final Aniversariante newObj = buscar(id);

		newObj.setDataNascimento(DataUtil.converterStringLocalDate(obj.getDataNascimento(), "yyyy-MM-dd"));
		newObj.setEmail(obj.getEmail());
		newObj.setIgreja(Igreja.builder().id(obj.getIdIgreja()).build());
		newObj.setNome(obj.getNome());
		
		return aniversarianteRepository.save(newObj);
	}
}
