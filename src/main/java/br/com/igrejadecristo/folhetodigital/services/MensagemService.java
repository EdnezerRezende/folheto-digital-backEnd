package br.com.igrejadecristo.folhetodigital.services;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.MensagemDTO;
import br.com.igrejadecristo.folhetodigital.dto.MensagemNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.Igreja;
import br.com.igrejadecristo.folhetodigital.entidades.Mensagem;
import br.com.igrejadecristo.folhetodigital.respositories.IgrejaRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MensagemRepository;
import br.com.igrejadecristo.folhetodigital.services.exceptions.ObjectNotFoundException;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository mensagemDao;
	
	@Autowired
	private IgrejaRepository igrejaDao;
	
	public List<Mensagem> buscarTodos() {
		List<Mensagem> mensagens = mensagemDao.findAllByOrderByDataCriado();
		return mensagens;
	}
	
	public MensagemDTO buscarPorMensagem(Integer idMensagem) {
		Mensagem mensagem = mensagemDao.findById(idMensagem).get();
		return new MensagemDTO(mensagem);
	}
	
	@Transactional
	public Mensagem salvarMensagem(MensagemNewDTO dto) {
		if (dto.getId() != null) {
			Boolean existeMensagem = mensagemDao.existsById(dto.getId());
			if (!existeMensagem) {
				throw new RuntimeException("Ocorreu um erro, mensagem não existe no sistema!");
			}
		}
		
		Igreja igreja = igrejaDao.findById((dto.getIgrejaId())).get();
		
		Mensagem mensagem = new Mensagem(dto.getId(),
				dto.getMensagem(), 
				dto.getAutor(), LocalDateTime.now(),  dto.getTitulo(), igreja);
		return mensagemDao.save(mensagem);
	}
	
	@Transactional
	public void deletarMensagem(Integer idMensagem) {
		
		mensagemDao.deleteById(idMensagem);
	}
	
	
	@Transactional
	public Mensagem buscarMensagemPorIdIgrejaEDataCriado(Integer idIgreja, LocalDateTime dataCriado, LocalDateTime dataLimiteBusca) {
		Mensagem mensagem = mensagemDao.buscaMensagemPorIdIgrejaAndDataCriado(idIgreja, dataCriado, dataLimiteBusca );
		if(mensagem == null) {
			throw new ObjectNotFoundException("Não Existe Mensagem. Necessário cadastrar. " + Mensagem.class.getName());
		}
		return mensagem; 
	}
}
