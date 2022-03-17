package br.com.pic.folheto.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pic.folheto.entidades.OfertaServico;

@Repository
public interface OfertaServicoRepository extends JpaRepository<OfertaServico, Integer>{
	
	@Transactional(readOnly=true)
	public List<OfertaServico> findByIgrejaId(Integer idIgreja);
	
	@Transactional(readOnly=true)
	public List<OfertaServico> findAllByOrderByDataCriacaoDesc();
}
