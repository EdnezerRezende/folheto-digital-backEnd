package br.com.igrejadecristo.folhetodigital.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.Aniversariante;

@Repository
public interface AniversarianteRepository extends JpaRepository<Aniversariante, Integer>{
	
	@Transactional(readOnly=true)
	public List<Aniversariante> findByIgrejaId(Integer idIgreja);

	@Query(value = "select a from Aniversariante a where a.igreja.id = :idIgreja and"
			+ " (day(a.dataNascimento) >= :diaInicio and month(a.dataNascimento) >= :mesInicio )"
			+ " or (day(a.dataNascimento) <= :diaFim and month(a.dataNascimento) <= :mesFim )")
	public List<Aniversariante> buscaAniversariantesPorIdIgrejaAndDataCriado(@Param("idIgreja") Integer idIgreja, 
			@Param("diaInicio") Integer diaInicio,
			@Param("mesInicio") Integer mesInicio,
			@Param("diaFim") Integer diaFim,
			@Param("mesFim") Integer mesFim
			);
}
