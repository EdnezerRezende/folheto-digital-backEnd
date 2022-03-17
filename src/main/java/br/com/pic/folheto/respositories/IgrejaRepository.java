package br.com.pic.folheto.respositories;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.pic.folheto.entidades.Igreja;

@Repository
public interface IgrejaRepository extends JpaRepository<Igreja, Integer>{
	@Transactional(readOnly=true)
	public List<Igreja> findAllByOrderByNome();
	
	public Optional<Igreja> findById(Integer id);
}
