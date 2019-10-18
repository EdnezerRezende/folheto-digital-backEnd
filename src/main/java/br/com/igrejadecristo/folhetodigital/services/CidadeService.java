package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Cidade;
import br.com.igrejadecristo.folhetodigital.respositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeDao;
	
	public List<Cidade> findByEstado(Integer estadoId) {
		return cidadeDao.findCidades(estadoId);
	}
	
	
	
}
