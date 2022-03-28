package br.com.pic.folheto.services;

import br.com.pic.folheto.entidades.Estado;
import br.com.pic.folheto.respositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoDao;
	
	public List<Estado> buscarTodos() {
		return estadoDao.findAllByOrderByNome();
	}
	
	
	
}
