package br.com.igrejadecristo.folhetodigital.services;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.igrejadecristo.folhetodigital.dto.DevocionalComentarioNewDTO;
import br.com.igrejadecristo.folhetodigital.entidades.DevocionalComentario;
import br.com.igrejadecristo.folhetodigital.entidades.Membro;
import br.com.igrejadecristo.folhetodigital.entidades.Referencia;
import br.com.igrejadecristo.folhetodigital.respositories.DevocionalComentarioRepository;
import br.com.igrejadecristo.folhetodigital.respositories.MembroRepository;
import br.com.igrejadecristo.folhetodigital.respositories.ReferenciaRepository;

@Service
public class DevocionalComentarioService {

	@Autowired
	private DevocionalComentarioRepository devocionalComentarioDao;
	
	@Autowired
	private ReferenciaRepository referenciaRepository;
	
	@Autowired
	private MembroRepository membroRepository;

	Base64 base64 = new Base64();
	
	public DevocionalComentario buscarPorReferenciaEMembro(Integer idMembro, Integer IdReferencia) {
		DevocionalComentario comentario = devocionalComentarioDao.findByMembroIdAndReferenciaId(idMembro, IdReferencia);
		if(comentario != null) {
			comentario.setChamouAtencao(new String(base64.decode(comentario.getChamouAtencao())));
			comentario.setoQueAprendi(new String(base64.decode(comentario.getoQueAprendi())));
			comentario.setSobreDeus(new String(base64.decode(comentario.getSobreDeus())));
			comentario.setSobreHumanidade(new String(base64.decode(comentario.getSobreHumanidade())));
			
		}
		
		return comentario;
	}

	@Transactional
	public DevocionalComentario salvar(DevocionalComentarioNewDTO dto) {

		Referencia referencia = referenciaRepository.findById(dto.getReferencia()).get();
		
		Membro membro = membroRepository.findById(dto.getIdMembro()).get();
		
		DevocionalComentario comentario = new DevocionalComentario(dto, referencia, membro);
		
		
		if (comentario.getChamouAtencao() == null) {
			comentario.setChamouAtencao("");
		}
		if (comentario.getoQueAprendi() == null) {
			comentario.setoQueAprendi("");
		}
		if (comentario.getSobreDeus() == null) {
			comentario.setSobreDeus("");
		}
		if (comentario.getSobreHumanidade() != null) {
			comentario.setSobreHumanidade("");
		}
		
		comentario.setChamouAtencao(base64.encodeAsString(comentario.getChamouAtencao().getBytes()));
		comentario.setoQueAprendi(base64.encodeAsString(comentario.getoQueAprendi().getBytes()));
		comentario.setSobreDeus(base64.encodeAsString(comentario.getSobreDeus().getBytes()));
		comentario.setSobreHumanidade(base64.encodeAsString(comentario.getSobreHumanidade().getBytes()));
		
		return devocionalComentarioDao.save(comentario);
	}

	public void deletar(Integer id) {
		devocionalComentarioDao.deleteById(id);
	}
}
