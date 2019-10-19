package br.com.igrejadecristo.folhetodigital.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.Folheto;

@Repository
public interface FolhetoRepository extends JpaRepository<Folheto, Integer>{
	@Transactional(readOnly=true)
	public List<Folheto> findAllByOrderByNome();
}
