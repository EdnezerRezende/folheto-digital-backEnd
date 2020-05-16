package br.com.igrejadecristo.folhetodigital.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.igrejadecristo.folhetodigital.entidades.Versiculo;

@Repository
public interface VersiculoRepository extends JpaRepository<Versiculo, Integer>{
	
}
