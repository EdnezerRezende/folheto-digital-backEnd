package br.com.igrejadecristo.folhetodigital.respositories;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.Missao;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Integer>{
	@Transactional(readOnly=true)
	public List<Missao> findAllByOrderByDataCriado();
	
	@Transactional(readOnly=true)
	@Query(value = "select m from Missao m where m.igreja.id = :idIgreja and m.dataCriado between :dataCriado and :dataLimiteBusca")
	public Missao buscaMissaoPorIdIgrejaAndDataCriado(@Param("idIgreja") Integer idIgreja, 
			@Param("dataCriado") LocalDateTime dataCriado,
			@Param("dataLimiteBusca") LocalDateTime dataLimiteBusca);
	
}
