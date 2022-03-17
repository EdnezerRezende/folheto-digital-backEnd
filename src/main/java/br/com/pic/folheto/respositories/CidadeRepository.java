package br.com.pic.folheto.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pic.folheto.entidades.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer>{
	
	@Transactional(readOnly=true)
	@Query("Select obj FROM Cidade obj where obj.estado.id = :estadoId Order By obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estadoId);
	
}
