package br.com.igrejadecristo.folhetodigital.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.AgendaEvento;

@Repository
public interface AgendaEventoRepository extends JpaRepository<AgendaEvento, Integer>{
	
	@Transactional(readOnly=true)
	public List<AgendaEvento> findByIgrejaId(Integer idIgreja);
	
	@Transactional(readOnly=true)
	public List<AgendaEvento> findAllByOrderByDiaSemanaAtividade();
	
	@Transactional(readOnly=true)
	public Boolean existsByTitulo(String titulo);
}
