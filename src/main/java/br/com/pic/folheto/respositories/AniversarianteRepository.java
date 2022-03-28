package br.com.pic.folheto.respositories;


import br.com.pic.folheto.entidades.Aniversariante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AniversarianteRepository extends JpaRepository<Aniversariante, Integer>{
	
	@Transactional(readOnly=true)
	public List<Aniversariante> findByIgrejaId(final Integer idIgreja);

	@Query(value = "select a from Aniversariante a where a.igreja.id = :idIgreja and" 
			+ " DATE_FORMAT(a.dataNascimento, '%m %d') between :dataInicio and :dataFim order by a.nome")
	public List<Aniversariante> buscaAniversariantesPorIdIgrejaAndDataCriado(final @Param("idIgreja") Integer idIgreja,
			final @Param("dataInicio") String dataInicio,
			final @Param("dataFim") String dataFim
			);
	
}
