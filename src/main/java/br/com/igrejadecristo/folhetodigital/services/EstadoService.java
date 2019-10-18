package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Estado;
import br.com.igrejadecristo.folhetodigital.respositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoDao;
	
	public List<Estado> buscarTodos() {
		return estadoDao.findAllByOrderByNome();
	}
	
	
	
}
