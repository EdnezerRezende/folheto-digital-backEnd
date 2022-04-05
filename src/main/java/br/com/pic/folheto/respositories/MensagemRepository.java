package br.com.pic.folheto.respositories;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pic.folheto.entidades.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer>{
	@Transactional(readOnly=true)
	public List<Mensagem> findAllByOrderByDataCriadoDesc();
	
	@Transactional(readOnly=true)
	@Query(value = "select m from Mensagem m where m.igreja.id = :idIgreja and m.dataCriado between :dataCriado and :dataLimiteBusca")
	public List<Mensagem> buscaMensagemPorIdIgrejaAndDataCriado(@Param("idIgreja") Integer idIgreja,
			@Param("dataCriado") LocalDateTime dataCriado,
			@Param("dataLimiteBusca") LocalDateTime dataLimiteBusca);
}
