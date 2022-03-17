package br.com.pic.folheto.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pic.folheto.entidades.Versiculo;

@Repository
public interface VersiculoRepository extends JpaRepository<Versiculo, Integer>{
	
}
