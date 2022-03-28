package br.com.pic.folheto.services;

import java.util.List;

import br.com.pic.folheto.respositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pic.folheto.entidades.Cidade;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeDao;
	
	public List<Cidade> findByEstado(final Integer estadoId) {
		return cidadeDao.findCidades(estadoId);
	}
	
	
	
}
