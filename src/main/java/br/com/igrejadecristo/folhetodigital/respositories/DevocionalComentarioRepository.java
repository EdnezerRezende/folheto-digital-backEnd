package br.com.igrejadecristo.folhetodigital.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.igrejadecristo.folhetodigital.entidades.DevocionalComentario;

@Repository
public interface DevocionalComentarioRepository extends JpaRepository<DevocionalComentario, Integer>{
	
	public DevocionalComentario findByMembroIdAndReferenciaIdAndIsDeletado(Integer idMembro, Integer IdReferencia, Boolean isDeletado);
	public Boolean existsByMembroIdAndReferenciaIdAndIsDeletado(Integer idMembro, Integer IdReferencia, Boolean isDeletado);
	
}
