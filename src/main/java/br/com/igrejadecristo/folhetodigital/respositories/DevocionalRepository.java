package br.com.igrejadecristo.folhetodigital.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.Devocional;

@Repository
public interface DevocionalRepository extends JpaRepository<Devocional, Integer>{
	
	@Transactional(readOnly=true)
	public List<Devocional> findByIgrejaId(Integer idIgreja);
	
	@Transactional(readOnly=true)
	public List<Devocional> findAllByOrderByDataCriacao();
	
	@Transactional(readOnly=true)
	public Boolean existsByReferencia(String referencia);
}
