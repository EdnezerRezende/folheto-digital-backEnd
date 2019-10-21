package br.com.igrejadecristo.folhetodigital.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.MensagemDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository mensagemDao;
	
	public List<Mensagem> buscarTodos() {
		return mensagemDao.findAllByOrderByDataCriado();
	}
	
	public MensagemDTO buscarPorFolheto(Integer idFolheto) {
		Mensagem mensagem = mensagemDao.findByFolhetosId(idFolheto);
		MensagemDTO dto = new MensagemDTO(mensagem);
		return dto;
	}
	
}
