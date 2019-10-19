package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Folheto;
import br.com.igrejadecristo.folhetodigital.respositories.FolhetoRepository;

@Service
public class FolhetoService {

	@Autowired
	private FolhetoRepository folhetoDao;
	
	public List<Folheto> buscarTodos() {
		return folhetoDao.findAllByOrderByNome();
	}
	
	
	
}
