package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.PequenoGrupo;
import br.com.igrejadecristo.folhetodigital.respositories.PequenoGrupoRepository;

@Service
public class PGService {

	@Autowired
	private PequenoGrupoRepository pgDao;
	
	public List<PequenoGrupo> buscarTodos() {
		return pgDao.findAll();
	}
	
	public List<PequenoGrupo> buscarPorIgreja(Integer idIgreja) {
		return pgDao.findByIgrejaId(idIgreja);
	}
	
}
