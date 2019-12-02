package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;

@Service
public class IgrejaService {

	@Autowired
	private IgrejaRepository igrejaDao;
	
	public List<Igreja> buscarTodos() {
		return igrejaDao.findAllByOrderByNome();
	}
	
	
	
}
