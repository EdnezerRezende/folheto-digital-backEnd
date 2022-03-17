package br.com.pic.folheto.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pic.folheto.entidades.Referencia;

@Repository
public interface ReferenciaRepository extends JpaRepository<Referencia, Integer>{
	
}
