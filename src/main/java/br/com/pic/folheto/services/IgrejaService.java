package br.com.pic.folheto.services;

import java.util.List;

import br.com.pic.folheto.respositories.IgrejaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pic.folheto.dto.IgrejaInfoDTO;
import br.com.pic.folheto.entidades.Igreja;

@Service
public class IgrejaService {

	@Autowired
	private IgrejaRepository igrejaDao;
	
	public List<Igreja> buscarTodos() {
		return igrejaDao.findAllByOrderByNome();
	}
	
	public IgrejaInfoDTO findById(Integer id) {
		Igreja igreja = igrejaDao.findById(id).get();
		
		return new IgrejaInfoDTO(igreja);
	}
	
	
}
