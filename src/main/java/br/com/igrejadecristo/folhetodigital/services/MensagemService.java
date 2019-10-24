package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.MensagemDTO;
import br.com.igrejadecristo.folhetodigital.dto.MensagemNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Folheto;
import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.respositories.FolhetoRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository mensagemDao;
	
	@Autowired
	private FolhetoRepository folhetoDao;
	
	public List<Mensagem> buscarTodos() {
		return mensagemDao.findAllByOrderByDataCriado();
	}
	
	public MensagemDTO buscarPorFolheto(Integer idFolheto) {
		Mensagem mensagem = mensagemDao.findByFolhetosId(idFolheto);
		MensagemDTO dto = new MensagemDTO(mensagem);
		return dto;
	}
	
	@Transactional
	public Mensagem salvarMensagem(MensagemNewDTO dto) {
		Folheto folhetim = folhetoDao.findById(dto.getId()).get();
		
		Mensagem mensagem = new Mensagem(null, dto.getMensagem(), dto.getAutor(), LocalDate.now(), folhetim);
		return mensagemDao.save(mensagem);
	}
}
