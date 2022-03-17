package br.com.pic.folheto.services;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.pic.folheto.dto.AniversarianteAlteraDTO;
import br.com.pic.folheto.dto.AniversarianteNewDTO;
import br.com.pic.folheto.entidades.Aniversariante;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.respositories.AniversarianteRepository;
import br.com.pic.folheto.services.exceptions.DataIntegrityException;
import br.com.pic.folheto.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class AniversarianteService {

	@Autowired
	private AniversarianteRepository aniversarianteRepository;
	
	@Transactional
	public Aniversariante buscar(Integer id) {
		Optional<Aniversariante> aniversariante = aniversarianteRepository.findById(id);
		return aniversariante
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado! id: " + id + ", tipo:" + Aniversariante.class.getName()));
	}
	
	@Transactional
	public Aniversariante salvarAniversariante(Aniversariante obj) {
		Aniversariante aniversariante = new Aniversariante();
		aniversariante.setDataNascimento(obj.getDataNascimento());
		aniversariante.setEmail(obj.getEmail());
		aniversariante.setNome(obj.getNome());
		aniversariante.setIgreja(obj.getIgreja());
		
		return aniversarianteRepository.save(aniversariante);
	}
	
	@Transactional
	public List<Aniversariante> buscarAniversariantesPorIdIgreja(Integer idIgreja){
		LocalDateTime dataHoje = LocalDateTime.now();

		LocalDate dataBoletimGerado = obterDataGeracaoBoletim(dataHoje.toLocalDate());
			
		LocalDate dataLimiteBusca = dataBoletimGerado.plusDays(6);
		
		return buscarAniversariantesPorIgrejaEDataNascimento(idIgreja, 
				dataBoletimGerado, dataLimiteBusca);
	}
	
	public LocalDate obterDataGeracaoBoletim(LocalDate dataDomingo) {
		DayOfWeek dayOfWeek = dataDomingo.getDayOfWeek();
		
		return validarEObterDataDomingo(dataDomingo, dayOfWeek);
	}
	
	private LocalDate validarEObterDataDomingo(LocalDate dataHoje, DayOfWeek dayOfWeek) {
		if(dayOfWeek== DayOfWeek.MONDAY) {
			dataHoje = LocalDate.now().minusDays(1);
		} else if(dayOfWeek== DayOfWeek.TUESDAY) {
			dataHoje = dataHoje.minusDays(2);
		} else if(dayOfWeek== DayOfWeek.WEDNESDAY) {
			dataHoje = dataHoje.minusDays(3);
		} else if(dayOfWeek== DayOfWeek.THURSDAY) {
			dataHoje = dataHoje.minusDays(4);
		} else if(dayOfWeek== DayOfWeek.FRIDAY) {
			dataHoje = dataHoje.minusDays(5);
		} else if(dayOfWeek== DayOfWeek.SATURDAY) {
			dataHoje = dataHoje.minusDays(6);
		}
		return dataHoje;
	}
	
	@Transactional
	public List<Aniversariante> buscarAniversariantesPorIgrejaEDataNascimento(Integer idIgreja, LocalDate dataInicio, LocalDate dataFim){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd");
		return aniversarianteRepository.buscaAniversariantesPorIdIgrejaAndDataCriado(idIgreja, dataInicio.format(formatter), dataFim.format(formatter));
	}

	public void delete(Integer id) {
		buscar(id);
		try {
			aniversarianteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há algo associada");
		}
	}
	
	public Aniversariante fromDTO(AniversarianteNewDTO objDto) {
		Igreja igreja = new Igreja(objDto.getIdIgreja(), null, null, null);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dataNascimento = LocalDate.parse(objDto.getDataNascimento(), formatter);

		return new Aniversariante(null, objDto.getNome(), dataNascimento, objDto.getEmail(), igreja);
	}
	
	@Transactional
	public Aniversariante updateDados(AniversarianteAlteraDTO obj, Integer id) {
		Aniversariante newObj = buscar(id);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dataNascimento = LocalDate.parse(obj.getDataNascimento(), formatter);
		newObj.setDataNascimento(dataNascimento);
		newObj.setEmail(obj.getEmail());
		newObj.setIgreja(new Igreja(obj.getIdIgreja(), null, null, null));
		newObj.setNome(obj.getNome());
		
		newObj = aniversarianteRepository.save(newObj);
		
		return newObj;
	}
}
