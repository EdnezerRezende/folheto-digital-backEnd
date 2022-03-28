package br.com.pic.folheto.services;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pic.folheto.dto.DevocionalComentarioNewDTO;
import br.com.pic.folheto.entidades.DevocionalComentario;
import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.entidades.Referencia;
import br.com.pic.folheto.respositories.DevocionalComentarioRepository;
import br.com.pic.folheto.respositories.MembroRepository;
import br.com.pic.folheto.respositories.ReferenciaRepository;

@Service
public class DevocionalComentarioService {

	@Autowired
	private DevocionalComentarioRepository devocionalComentarioDao;
	
	@Autowired
	private ReferenciaRepository referenciaRepository;
	
	@Autowired
	private MembroRepository membroRepository;

	final Base64 base64 = new Base64();
	
	public DevocionalComentario buscarPorReferenciaEMembro(final Integer idMembro, final Integer IdReferencia) {
		final DevocionalComentario comentario = devocionalComentarioDao.findByMembroIdAndReferenciaIdAndIsDeletado(idMembro, IdReferencia, Boolean.FALSE);
		if(comentario != null) {
			desicriptografarComentario(comentario);

		}
		
		return comentario;
	}

	private void desicriptografarComentario(final DevocionalComentario comentario) {
		if (comentario.getChamouAtencao() != null) {
			comentario.setChamouAtencao(new String(base64.decode(comentario.getChamouAtencao())));
		}
		if (comentario.getOQueAprendi() != null) {
			comentario.setOQueAprendi(new String(base64.decode(comentario.getOQueAprendi())));
		}
		if (comentario.getSobreDeus() != null) {
			comentario.setSobreDeus(new String(base64.decode(comentario.getSobreDeus())));
		}
		if (comentario.getSobreHumanidade() != null) {
			comentario.setSobreHumanidade(new String(base64.decode(comentario.getSobreHumanidade())));
		}
	}

	@Transactional
	public DevocionalComentario salvar(final DevocionalComentarioNewDTO dto) {

		final Referencia referencia = referenciaRepository.findById(dto.getReferencia()).get();
		
		final Membro membro = membroRepository.findById(dto.getIdMembro()).get();
		
		final DevocionalComentario comentarioAtual = devocionalComentarioDao.findByMembroIdAndReferenciaIdAndIsDeletado(dto.getIdMembro(), dto.getReferencia(), Boolean.FALSE);
		
		final DevocionalComentario comentario = new DevocionalComentario(dto, referencia, membro);
		
		
		if (comentarioAtual != null ) {
			devocionalComentarioDao.deleteById(comentarioAtual.getId());
		}

		comentario.setId(null);
		criptografarComentario(comentario);

		return devocionalComentarioDao.save(comentario);
	}

	private void criptografarComentario(final DevocionalComentario comentario) {


		if (comentario.getChamouAtencao() != null) {
			comentario.setChamouAtencao(base64.encodeAsString(comentario.getChamouAtencao().getBytes()));
		}
		if (comentario.getOQueAprendi() != null) {
			comentario.setOQueAprendi(base64.encodeAsString(comentario.getOQueAprendi().getBytes()));
		}
		if (comentario.getSobreDeus() != null) {
			comentario.setSobreDeus(base64.encodeAsString(comentario.getSobreDeus().getBytes()));
		}
		if (comentario.getSobreHumanidade() != null) {
			comentario.setSobreHumanidade(base64.encodeAsString(comentario.getSobreHumanidade().getBytes()));
		}
	}

	public void deletar(final Integer id) {
		devocionalComentarioDao.deleteById(id);
	}
}
