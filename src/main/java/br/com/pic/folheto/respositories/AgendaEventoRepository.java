package br.com.pic.folheto.respositories;


import br.com.pic.folheto.entidades.AgendaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AgendaEventoRepository extends JpaRepository<AgendaEvento, Integer>{
	
	@Transactional(readOnly=true)
	public List<AgendaEvento> findByIgrejaId(final Integer idIgreja);
	
	@Transactional(readOnly=true)
	public List<AgendaEvento> findAllByOrderByDiaSemanaAtividade();
	
	@Transactional(readOnly=true)
	public Boolean existsByTituloAndDiaSemanaAtividade(final String titulo,final String dia);
}
