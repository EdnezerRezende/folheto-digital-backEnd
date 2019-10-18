package br.com.igrejadecristo.folhetodigital.respositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.igrejadecristo.folhetodigital.entidades.Membro;

@Repository
public interface MembroRepository extends JpaRepository<Membro, Integer>{

	@Transactional(readOnly=true)
	public Membro findByEmail(String email);
}
