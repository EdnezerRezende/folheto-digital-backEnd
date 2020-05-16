package br.com.igrejadecristo.folhetodigital.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.igrejadecristo.folhetodigital.entidades.Referencia;

@Repository
public interface ReferenciaRepository extends JpaRepository<Referencia, Integer>{
	
}
