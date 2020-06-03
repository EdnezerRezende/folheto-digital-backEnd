package br.com.igrejadecristo.folhetodigital.respositories;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.Devocional;

@Repository
public interface DevocionalRepository extends JpaRepository<Devocional, Integer>{
	
	@Transactional(readOnly=true)
	public List<Devocional> findByIgrejaIdAndIsDeletado(Integer idIgreja, Boolean isDeletado);
	
	@Transactional(readOnly=true)
	public List<Devocional> findAllByIsDeletadoOrderByDataCriacaoDesc(Boolean isDeletado);
	
	@Query(value = "select d from Devocional d where d.igreja.id = :idIgreja and d.dataCriacao between :dataCriado and :dataLimiteBusca and d.isDeletado = :isDeletado")
	public List<Devocional> buscaDevocionalPorIdIgrejaAndDataCriado(@Param("idIgreja") Integer idIgreja, 
			@Param("isDeletado") Boolean isDeletado,
			@Param("dataCriado") LocalDate dataCriado,
			@Param("dataLimiteBusca") LocalDate dataLimiteBusca);
}
