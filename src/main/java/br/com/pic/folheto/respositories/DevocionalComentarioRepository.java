package br.com.pic.folheto.respositories;


import br.com.pic.folheto.entidades.DevocionalComentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DevocionalComentarioRepository extends JpaRepository<DevocionalComentario, Integer>{
	
	public DevocionalComentario findByMembroIdAndReferenciaIdAndIsDeletado(Integer idMembro, Integer IdReferencia, Boolean isDeletado);
	public Boolean existsByMembroIdAndReferenciaIdAndIsDeletado(Integer idMembro, Integer IdReferencia, Boolean isDeletado);
	
}
