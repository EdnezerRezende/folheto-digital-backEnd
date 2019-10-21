package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.entidades.Folheto;
import br.com.igrejadecristo.folhetodigital.respositories.FolhetoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;

@Service
public class FolhetoService {

	@Autowired
	private FolhetoRepository folhetoDao;
	
	@Autowired
	private MensagemRepository mensagemDao;
	
	
	public List<Folheto> buscarTodos() {
		List<Folheto> folhetos = folhetoDao.findAllByOrderByDataPublicado();
		folhetos.stream().forEach(obj -> {
			obj.setMensagem(mensagemDao.findByFolhetosId(obj.getId()));
		});
		return folhetos;
	}
	
	
	
}
