package br.com.pic.folheto.services;

import java.util.List;

import br.com.pic.folheto.respositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pic.folheto.entidades.Estado;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoDao;
	
	public List<Estado> buscarTodos() {
		return estadoDao.findAllByOrderByNome();
	}
	
	
	
}
