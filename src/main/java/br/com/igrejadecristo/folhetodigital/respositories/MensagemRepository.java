package br.com.igrejadecristo.folhetodigital.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer>{
	@Transactional(readOnly=true)
	public List<Mensagem> findAllByOrderByDataCriado();
	
	@Transactional(readOnly=true)
	public Mensagem findByFolhetosId(Integer idFolheto);
}
