package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.AniversarianteAlteraDTO;
import br.com.igrejadecristo.folhetodigital.dto.AniversarianteNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Aniversariante;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.respositories.AniversarianteRepository;
import br.com.igrejadecristo.folhetodigital.services.exceptions.DataIntegrityException;

@Service
public class AniversarianteService {

	@Autowired
	private AniversarianteRepository aniversarianteRepository;
	
	@Transactional
	public Aniversariante buscar(Integer id) {
		Optional<Aniversariante> aniversariante = aniversarianteRepository.findById(id);
		return aniversariante
				.orElseThrow(() -> new br.com.igrejadecristo.folhetodigital.services.exceptions.ObjectNotFoundException(
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
		
		return aniversarianteRepository.findByIgrejaId(idIgreja); 
	}
	
	@Transactional
	public List<Aniversariante> buscarAniversariantesPorIgrejaEDataNascimento(Integer idIgreja, LocalDate dataInicio, LocalDate dataFim){
		return aniversarianteRepository.buscaAniversariantesPorIdIgrejaAndDataCriado(idIgreja, dataInicio.getDayOfMonth(), dataInicio.getMonthValue(), dataFim.getDayOfMonth(), dataFim.getMonthValue());
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