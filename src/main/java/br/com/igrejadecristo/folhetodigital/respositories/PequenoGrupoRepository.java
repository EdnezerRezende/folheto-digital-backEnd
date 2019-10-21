package br.com.igrejadecristo.folhetodigital.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;

@Repository
public interface PequenoGrupoRepository extends JpaRepository<PequenoGrupo, Integer>{
	
	@Transactional(readOnly=true)
	public List<PequenoGrupo> findByIgrejaId(Integer idIgreja);
}
