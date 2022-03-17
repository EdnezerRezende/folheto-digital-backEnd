package br.com.pic.folheto.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pic.folheto.entidades.EnderecoMembro;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoMembro, Integer>{
	
	public List<EnderecoMembro> findAllByMembroId(Integer membroId);
}
