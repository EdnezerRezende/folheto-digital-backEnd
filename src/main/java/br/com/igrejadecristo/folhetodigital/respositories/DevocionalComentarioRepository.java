package br.com.igrejadecristo.folhetodigital.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.igrejadecristo.folhetodigital.entidades.DevocionalComentario;

@Repository
public interface DevocionalComentarioRepository extends JpaRepository<DevocionalComentario, Integer>{
	
	public DevocionalComentario findByMembroIdAndReferenciaId(Integer idMembro, Integer IdReferencia);
	public Boolean existsByMembroIdAndReferenciaId(Integer idMembro, Integer IdReferencia);
}
