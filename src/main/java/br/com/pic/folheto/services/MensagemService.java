package br.com.pic.folheto.services;

import br.com.pic.folheto.dto.MensagemDTO;
import br.com.pic.folheto.dto.MensagemNewDTO;
import br.com.pic.folheto.entidades.Igreja;
import br.com.pic.folheto.entidades.Mensagem;
import br.com.pic.folheto.respositories.IgrejaRepository;
import br.com.pic.folheto.respositories.MensagemRepository;
import br.com.pic.folheto.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository mensagemDao;
	
	@Autowired
	private IgrejaRepository igrejaDao;
	
	public List<Mensagem> buscarTodos() {
		return mensagemDao.findAllByOrderByDataCriadoDesc();
	}
	
	public MensagemDTO buscarPorMensagem(final Integer idMensagem) {
		final Mensagem mensagem = mensagemDao.findById(idMensagem).get();
		return MensagemDTO.builder()
				.autor(mensagem.getAutor())
				.id(mensagem.getId())
				.mensagem(mensagem.getMensagem())
				.titulo(mensagem.getTitulo())
				.build();
	}
	
	@Transactional
	public Mensagem salvarMensagem(final MensagemNewDTO dto) {
		if (dto.getId() != null) {
			final Boolean existeMensagem = mensagemDao.existsById(dto.getId());
			if (!existeMensagem) {
				throw new RuntimeException("Ocorreu um erro, mensagem não existe no sistema!");
			}
		}
		
		final Igreja igreja = igrejaDao.findById((dto.getIgrejaId())).get();
		
		Mensagem mensagem = Mensagem.builder()
				.id(dto.getId())
				.mensagem(dto.getMensagem())
				.autor(dto.getAutor())
				.dataCriado(LocalDateTime.now())
				.titulo(dto.getTitulo())
				.igreja(igreja)
				.build();

		return mensagemDao.save(mensagem);
	}
	
	@Transactional
	public void deletarMensagem(final Integer idMensagem) {
		mensagemDao.deleteById(idMensagem);
	}
	
	
	@Transactional
	public Mensagem buscarMensagemPorIdIgrejaEDataCriado(final Integer idIgreja,
														 final LocalDateTime dataCriado,
														 final LocalDateTime dataLimiteBusca) {
		final Mensagem mensagem = mensagemDao.buscaMensagemPorIdIgrejaAndDataCriado(idIgreja, dataCriado, dataLimiteBusca );
		if(mensagem == null) {
			throw new ObjectNotFoundException("Não Existe Mensagem. Necessário cadastrar. " + Mensagem.class.getName());
		}
		return mensagem; 
	}
}
