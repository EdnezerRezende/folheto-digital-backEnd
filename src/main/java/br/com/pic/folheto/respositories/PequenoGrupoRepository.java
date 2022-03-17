package br.com.pic.folheto.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pic.folheto.entidades.PequenoGrupo;

@Repository
public interface PequenoGrupoRepository extends JpaRepository<PequenoGrupo, Integer>{
	
	@Transactional(readOnly=true)
	public List<PequenoGrupo> findByIgrejaId(Integer idIgreja);
	
	@Transactional(readOnly=true)
	public List<PequenoGrupo> findAllByOrderByDiaSemanaAtividade();
	
	@Transactional(readOnly=true)
	public Boolean existsByLiderAndResponsavelCasaAndDiaSemanaAtividade(String lider, String responsavel, String dia);
}
